package com.ts.marblearch.api.webAdapter.property;

import com.ts.marblearch.api.sharedKernel.application.events.IModuleClient;
import com.ts.marblearch.api.webAdapter.property.commands.*;
import com.ts.marblearch.api.webAdapter.property.queries.GetImageQuery;
import com.ts.marblearch.api.webAdapter.property.queries.GetPropertyQuery;
import com.ts.marblearch.api.webAdapter.property.queries.PageableSearchQuery;
import com.ts.marblearch.api.webAdapter.property.requests.PageableFilters;
import com.ts.marblearch.api.webAdapter.property.requests.create.CreatePropertyRequest;
import com.ts.marblearch.api.webAdapter.property.requests.update.UpdatePropertyRequest;
import com.ts.marblearch.api.webAdapter.property.responses.ImageDTO;
import com.ts.marblearch.api.webAdapter.property.responses.PropertyDTO;
import com.ts.marblearch.api.webAdapter.property.responses.PropertySummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/${api.version}/property-management")
@RequiredArgsConstructor
public class PropertyController {

    private final IModuleClient moduleClient;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER')")
    public CompletableFuture<ResponseEntity<UUID>> createProperty(@RequestBody CreatePropertyRequest request) {
        CompletableFuture<UUID> resultFuture = new CompletableFuture<>();
        var command = new CreatePropertyCommand(request, resultFuture);
        return moduleClient.executeCommandAsync(command)
                .thenApply(ResponseEntity::ok);
    }

    @PatchMapping("/{propertyUUID}/disable")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<ResponseEntity<Void>> disableProperty(@PathVariable UUID propertyUUID) {
        CompletableFuture<Void> resultFuture = new CompletableFuture<>();
        var command = new DisablePropertyCommand(propertyUUID, resultFuture);

        return moduleClient.executeCommandAsync(command)
                .thenApply(result -> ResponseEntity.ok().build());
    }

    @PatchMapping("/{propertyUUID}/enable")
    @PreAuthorize("hasRole('ADMIN')")
    public CompletableFuture<ResponseEntity<Void>> enableProperty(@PathVariable UUID propertyUUID) {
        CompletableFuture<Void> resultFuture = new CompletableFuture<>();
        var command = new EnablePropertyCommand(propertyUUID, resultFuture);

        return moduleClient.executeCommandAsync(command)
                .thenApply(result -> ResponseEntity.ok().build());
    }

    @PatchMapping("/{propertyUUID}/image/{imageUUID}/disable")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER')")
    public CompletableFuture<ResponseEntity<Void>> disableImage(@PathVariable UUID imageUUID, @PathVariable UUID propertyUUID) {
        CompletableFuture<Void> resultFuture = new CompletableFuture<>();
        var command = new DisableImageCommand(imageUUID, propertyUUID, resultFuture);

        return moduleClient.executeCommandAsync(command)
                .thenApply(result -> ResponseEntity.ok().build());
    }

    @PatchMapping("/{propertyUUID}/image/{imageUUID}/enable")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER')")
    public CompletableFuture<ResponseEntity<Void>> enableImage(@PathVariable UUID imageUUID, @PathVariable UUID propertyUUID) {
        CompletableFuture<Void> resultFuture = new CompletableFuture<>();
        var command = new EnableImageCommand(imageUUID, propertyUUID, resultFuture);

        return moduleClient.executeCommandAsync(command)
                .thenApply(result -> ResponseEntity.ok().build());
    }

    @PatchMapping("/{propertyId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER')")
    public CompletableFuture<ResponseEntity<Void>> updateProperty(@PathVariable UUID propertyId, @RequestBody UpdatePropertyRequest request) {
        CompletableFuture<Void> resultFuture = new CompletableFuture<>();
        var command = new com.ts.marblearch.api.webAdapter.property.commands.UpdatePropertyCommand(propertyId, request, resultFuture);
        return moduleClient.executeCommandAsync(command)
                .thenApply(result -> ResponseEntity.ok().build());
    }

    @GetMapping("/search")
    public CompletableFuture<ResponseEntity<Page<PropertySummaryDTO>>> pageableSearch(
            @RequestBody(required = false) PageableFilters filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        PageableFilters finalFilters = filters != null ? filters : new PageableFilters();
        CompletableFuture<Page<PropertySummaryDTO>> resultFuture = new CompletableFuture<>();
        var query = new PageableSearchQuery(finalFilters, page, size, resultFuture);

        return moduleClient.executeQueryAsync(query)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{propertyUUID}")
    public CompletableFuture<ResponseEntity<PropertyDTO>> getPropertyDetails(@PathVariable UUID propertyUUID) {
        CompletableFuture<PropertyDTO> resultFuture = new CompletableFuture<>();
        var query = new GetPropertyQuery(propertyUUID, resultFuture);

        return moduleClient.executeQueryAsync(query)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping(value = "/{propertyUUID}/image", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER')")
    public CompletableFuture<ResponseEntity<Void>> uploadImage(@RequestPart(value = "image") MultipartFile imageFile, @PathVariable UUID propertyUUID) {
        CompletableFuture<Void> resultFuture = new CompletableFuture<>();
        var command = new UploadImageCommand(propertyUUID, imageFile, resultFuture);

        return moduleClient.executeCommandAsync(command)
                .thenApply(result -> ResponseEntity.ok().build());
    }

    @GetMapping("/{propertyUUID}/image/{imageUUID}")
    public CompletableFuture<ResponseEntity<ImageDTO>> ImageURL(@PathVariable UUID propertyUUID, @PathVariable UUID imageUUID) {
        CompletableFuture<ImageDTO> resultFuture = new CompletableFuture<>();
        var query = new GetImageQuery(propertyUUID, imageUUID, resultFuture);

        return moduleClient.executeQueryAsync(query)
                .thenApply(ResponseEntity::ok);
    }
}
