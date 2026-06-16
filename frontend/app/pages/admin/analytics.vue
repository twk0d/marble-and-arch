<script setup lang="ts">
definePageMeta({
  layout: 'admin'
})

const stats = [
  { title: 'Visitas no Site (Mês)', value: '142.500', trend: '+12%', trendColor: 'text-success' },
  { title: 'Leads Gerados', value: '3.421', trend: '+5%', trendColor: 'text-success' },
  { title: 'Taxa de Conversão', value: '2.4%', trend: '-0.2%', trendColor: 'text-error' },
  { title: 'VGV Projetado', value: 'R$ 84M', trend: '+22%', trendColor: 'text-success' }
]

const topProperties = [
  { id: '1', name: 'Cobertura Duplex Faria Lima', clicks: 12500, leads: 42 },
  { id: '2', name: 'Mansão Condomínio Alphaville', clicks: 8400, leads: 15 },
  { id: '3', name: 'Apartamento Jardins', clicks: 7200, leads: 28 },
  { id: '4', name: 'Casa de Praia Baleia', clicks: 5100, leads: 8 }
]
</script>

<template>
  <div class="space-y-8 max-w-6xl">
    <div>
      <h1 class="text-h2 font-playfair text-foreground">Analytics e Relatórios</h1>
      <p class="text-body-base text-text-dimmed mt-1">Visão geral do tráfego e conversão de campanhas M&A.</p>
    </div>

    <!-- Stats Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <UCard v-for="(stat, idx) in stats" :key="idx" :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
        <p class="text-xs font-medium text-text-dimmed uppercase tracking-wider">{{ stat.title }}</p>
        <div class="mt-2 flex items-baseline gap-3">
          <p class="text-h3 font-playfair text-foreground">{{ stat.value }}</p>
          <span :class="['text-sm font-medium', stat.trendColor]">{{ stat.trend }}</span>
        </div>
      </UCard>
    </div>

    <!-- Charts area -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <UCard class="lg:col-span-2" :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
        <template #header>
          <h3 class="font-medium text-foreground">Tráfego de Visitantes (Últimos 7 dias)</h3>
        </template>
        <!-- Mock Bar Chart -->
        <div class="h-64 flex items-end justify-between gap-2 pt-4">
          <div v-for="(day, i) in [40, 60, 45, 80, 50, 90, 100]" :key="i" class="w-full h-full flex flex-col justify-end items-center gap-2 group">
            <div class="w-full bg-primary/20 rounded-t-sm relative group-hover:bg-primary transition-colors duration-300" :style="{ height: `${day}%` }">
               <span class="absolute -top-8 left-1/2 -translate-x-1/2 text-xs font-medium opacity-0 group-hover:opacity-100 transition-opacity bg-card px-2 py-1 rounded shadow-sm border border-border">{{ day }}k</span>
            </div>
            <span class="text-xs text-text-dimmed">{{ ['Seg','Ter','Qua','Qui','Sex','Sáb','Dom'][i] }}</span>
          </div>
        </div>
      </UCard>

      <UCard :ui="{ ring: 'ring-1 ring-border shadow-sm' }">
        <template #header>
          <h3 class="font-medium text-foreground">Top Imóveis (Leads)</h3>
        </template>
        <div class="space-y-6">
          <div v-for="prop in topProperties" :key="prop.id">
            <div class="flex justify-between text-sm mb-2">
              <span class="font-medium text-foreground truncate max-w-[150px]" :title="prop.name">{{ prop.name }}</span>
              <span class="text-text-dimmed">{{ prop.leads }} leads</span>
            </div>
            <UProgress :model-value="prop.clicks" :max="15000" color="primary" size="sm" />
          </div>
        </div>
      </UCard>
    </div>
  </div>
</template>
