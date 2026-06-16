<script setup lang="ts">
import type { LeadDTO } from '~/types'

definePageMeta({
  layout: 'admin'
})

const columns = [
  { accessorKey: 'customerName', header: 'Nome do Cliente' },
  { accessorKey: 'propertyId', header: 'Imóvel (ID)' },
  { accessorKey: 'createdAt', header: 'Data de Entrada' },
  { accessorKey: 'status', header: 'Status' },
  { accessorKey: 'actions', header: 'Ações' }
]

const leads = ref<LeadDTO[]>([
  {
    id: 'L-001',
    propertyId: '1024',
    customerName: 'João Silva',
    customerEmail: 'joao.silva@example.com',
    customerPhone: '(11) 98888-7777',
    message: 'Tenho interesse em agendar uma visita para este final de semana.',
    createdAt: '2026-06-11T10:30:00Z',
    status: 'NEW'
  },
  {
    id: 'L-002',
    propertyId: '1080',
    customerName: 'Maria Souza',
    customerEmail: 'maria.souza@exemplo.com',
    createdAt: '2026-06-09T14:15:00Z',
    status: 'CONTACTED'
  },
  {
    id: 'L-003',
    propertyId: '900',
    customerName: 'Carlos Mendes',
    customerEmail: 'carlos@mail.com',
    createdAt: '2026-06-05T09:00:00Z',
    status: 'ARCHIVED'
  }
])

const statusColors = {
  NEW: 'primary',
  CONTACTED: 'success',
  ARCHIVED: 'neutral'
}

const statusLabels = {
  NEW: 'Novo',
  CONTACTED: 'Contatado',
  ARCHIVED: 'Arquivado'
}

function formatDate(dateStr: string) {
  return new Date(dateStr).toLocaleDateString('pt-BR', { day: '2-digit', month: 'short', year: 'numeric' })
}

function updateStatus(leadId: string, newStatus: 'NEW' | 'CONTACTED' | 'ARCHIVED') {
  const lead = leads.value.find(l => l.id === leadId)
  if (lead) {
    lead.status = newStatus
    useToast().add({ title: 'Status Atualizado', description: `O lead foi marcado como ${statusLabels[newStatus]}.`, color: 'success' })
  }
}
</script>

<template>
  <div class="space-y-8">
    <!-- Header -->
    <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
      <div>
        <h1 class="text-h2 text-foreground">Gestão de Leads</h1>
        <p class="text-body-sm text-text-dimmed mt-1">Acompanhe e converta as oportunidades geradas pelos seus anúncios.</p>
      </div>
      <div class="flex flex-col sm:flex-row gap-3">
        <UInput icon="i-heroicons-magnifying-glass" placeholder="Buscar cliente ou ID..." class="w-full sm:w-64" />
        <UButton color="neutral" variant="outline" icon="i-heroicons-funnel">Filtros</UButton>
      </div>
    </div>

    <!-- Tabela de Leads CRM -->
    <UCard class="bg-card border-border shadow-sm" :ui="{ body: 'p-0 sm:p-0' }">
      <UTable 
        :columns="columns" 
        :data="leads" 
        class="w-full"
        :ui="{ 
          th: { base: 'uppercase text-xs font-semibold tracking-wider text-text-dimmed bg-muted/50 py-4' },
          td: { base: 'whitespace-nowrap align-middle py-4 border-t border-border' }
        }"
      >
        <!-- Custom Customer Column -->
        <template #customerName-cell="{ row }">
          <div class="flex flex-col">
            <span class="font-medium text-foreground">{{ row.original.customerName }}</span>
            <span class="text-xs text-text-dimmed">{{ row.original.customerEmail }}</span>
          </div>
        </template>

        <!-- Custom Property ID Link -->
        <template #propertyId-cell="{ row }">
          <NuxtLink :to="`/properties/${row.original.propertyId}`" class="text-primary hover:underline font-medium text-sm flex items-center gap-1">
            #{{ row.original.propertyId }}
            <UIcon name="i-heroicons-arrow-top-right-on-square" class="w-3 h-3" />
          </NuxtLink>
        </template>
        
        <!-- Custom Date -->
        <template #createdAt-cell="{ row }">
          <span class="text-sm text-text-dimmed">{{ formatDate(row.original.createdAt) }}</span>
        </template>

        <!-- Custom Status Badge -->
        <template #status-cell="{ row }">
          <UBadge :color="statusColors[row.original.status]" variant="subtle" size="xs" class="font-medium">
            {{ statusLabels[row.original.status] }}
          </UBadge>
        </template>
        
        <!-- Dropdown Actions -->
        <template #actions-cell="{ row }">
          <div class="flex justify-end">
            <UDropdownMenu :items="[[
              { label: 'Marcar como Contatado', icon: 'i-heroicons-check-circle', disabled: row.original.status === 'CONTACTED', onSelect: () => updateStatus(row.original.id, 'CONTACTED') },
              { label: 'Ver Mensagem', icon: 'i-heroicons-chat-bubble-left-ellipsis' },
              { label: 'Arquivar Lead', icon: 'i-heroicons-archive-box', disabled: row.original.status === 'ARCHIVED', onSelect: () => updateStatus(row.original.id, 'ARCHIVED') }
            ]]" :content="{ align: 'end' }">
              <UButton color="neutral" variant="ghost" icon="i-heroicons-ellipsis-horizontal" />
            </UDropdownMenu>
          </div>
        </template>
      </UTable>

      <template #footer>
        <div class="flex justify-between items-center p-4 border-t border-border">
          <span class="text-sm text-text-dimmed">Mostrando 3 de 3 leads</span>
          <UPagination :total="3" :page-count="10" :model-value="1" />
        </div>
      </template>
    </UCard>
  </div>
</template>
