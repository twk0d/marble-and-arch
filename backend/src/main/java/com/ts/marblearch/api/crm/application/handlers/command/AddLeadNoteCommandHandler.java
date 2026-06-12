package com.ts.marblearch.api.crm.application.handlers.command;

import com.github.f4b6a3.ulid.UlidCreator;
import com.ts.marblearch.api.crm.application.ILeadRepository;
import com.ts.marblearch.api.crm.webAdapter.commands.AddLeadNoteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AddLeadNoteCommandHandler {

    private final ILeadRepository leadRepository;

    @EventListener
    @Transactional
    public void handle(AddLeadNoteCommand command) {
        var request = command.getRequest();
        var lead = leadRepository.findById(request.getLeadId())
                .orElseThrow(() -> new RuntimeException("Lead not found"));

        lead.addNote(UlidCreator.getMonotonicUlid().toUuid(), request.getBrokerId(), request.getContent());
        leadRepository.save(lead);
        command.getResultFuture().complete(null);
    }
}
