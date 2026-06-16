<script setup lang="ts">
definePageMeta({
  layout: 'admin'
})

const kpi = [
  { label: 'Receita Total (Mês)', value: 'R$ 1.250.000', icon: 'i-heroicons-arrow-trending-up', color: 'text-success' },
  { label: 'Despesas Operacionais', value: 'R$ 340.000', icon: 'i-heroicons-arrow-trending-down', color: 'text-error' },
  { label: 'Comissões Pagas', value: 'R$ 410.000', icon: 'i-heroicons-banknotes', color: 'text-primary' },
  { label: 'Lucro Líquido', value: 'R$ 500.000', icon: 'i-heroicons-wallet', color: 'text-success' }
]

const transactions = ref([
  { id: 'TRX-1092', type: 'Receita', description: 'Venda Cobertura Jardins', amount: 'R$ 250.000', date: '10/11/2026', status: 'Concluído' },
  { id: 'TRX-1093', type: 'Comissão', description: 'Pagamento Corretor João Silva', amount: 'R$ -100.000', date: '10/11/2026', status: 'Concluído' },
  { id: 'TRX-1094', type: 'Despesa', description: 'Marketing RD Station Anual', amount: 'R$ -15.000', date: '08/11/2026', status: 'Agendado' },
  { id: 'TRX-1095', type: 'Receita', description: 'Venda Apartamento Itaim', amount: 'R$ 180.000', date: '05/11/2026', status: 'Processando' }
])

const columns = [
  { accessorKey: 'id', header: 'ID' },
  { accessorKey: 'type', header: 'Tipo' },
  { accessorKey: 'description', header: 'Descrição' },
  { accessorKey: 'amount', header: 'Valor' },
  { accessorKey: 'date', header: 'Data' },
  { accessorKey: 'status', header: 'Status' }
]
</script>

<template>
  <div class="space-y-8 max-w-6xl">
    <div>
      <h1 class="text-h2 font-playfair text-foreground">Gestão Financeira</h1>
      <p class="text-body-base text-text-dimmed mt-1">Acompanhe o fluxo de caixa, pagamentos e comissões da imobiliária.</p>
    </div>

    <!-- KPIs -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
      <UCard v-for="(item, i) in kpi" :key="i" :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
        <div class="flex items-center gap-4">
          <div :class="`p-3 rounded-full bg-muted ${item.color}`">
            <UIcon :name="item.icon" class="w-6 h-6" />
          </div>
          <div>
            <p class="text-xs font-medium text-text-dimmed">{{ item.label }}</p>
            <p class="text-h4 font-playfair text-foreground mt-1">{{ item.value }}</p>
          </div>
        </div>
      </UCard>
    </div>

    <!-- Transactions Table -->
    <UCard :ui="{ ring: 'ring-1 ring-border shadow-sm', body: { padding: '' } }">
      <template #header>
        <div class="flex justify-between items-center p-4">
          <h3 class="font-medium text-foreground">Lançamentos Recentes</h3>
          <UButton color="neutral" variant="outline" icon="i-heroicons-arrow-down-tray" size="xs">Exportar CSV</UButton>
        </div>
      </template>

      <UTable :data="transactions" :columns="columns">
        <template #type-cell="{ row }">
          <UBadge :color="row.original.type === 'Receita' ? 'success' : (row.original.type === 'Comissão' ? 'primary' : 'error')" variant="soft">
            {{ row.original.type }}
          </UBadge>
        </template>
        
        <template #amount-cell="{ row }">
          <span :class="row.original.amount.includes('-') ? 'text-error font-medium' : 'text-success font-medium'">
            {{ row.original.amount }}
          </span>
        </template>
        
        <template #status-cell="{ row }">
          <div class="flex items-center gap-2">
            <div :class="`w-2 h-2 rounded-full ${row.original.status === 'Concluído' ? 'bg-success' : (row.original.status === 'Agendado' ? 'bg-amber-500' : 'bg-primary')}`"></div>
            <span class="text-sm text-text-dimmed">{{ row.original.status }}</span>
          </div>
        </template>
      </UTable>
    </UCard>
  </div>
</template>
