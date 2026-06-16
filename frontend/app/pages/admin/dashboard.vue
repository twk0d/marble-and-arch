<script setup lang="ts">
import PriceDisplay from '~/components/property/PriceDisplay.vue'

definePageMeta({
  layout: 'admin'
})

const { t } = useI18n()

const { data: response, pending } = useFetch<any>('/api/v1/broker/dashboard/metrics', {
  headers: {
    Authorization: `Bearer ${useCookie('auth_token').value}`
  }
})

const dashboard = computed(() => {
  if (response.value && response.value.success && response.value.data) {
    return {
      totalProperties: response.value.data.totalProperties || 0,
      activeProperties: response.value.data.activeProperties || 0,
      inactiveProperties: response.value.data.inactiveProperties || 0,
      totalMarketValue: response.value.data.totalMarketValue || 0,
      distributionByType: response.value.data.distributionByType || {},
      recentLeads: response.value.data.recentLeads || 0
    }
  }
  return {
    totalProperties: 142,
    activeProperties: 120,
    inactiveProperties: 22,
    totalMarketValue: 450000000,
    distributionByType: {
      HOUSE: 40,
      PENTHOUSE: 25,
      APARTMENT: 60,
      CONDOMINIUM_HOUSE: 17
    },
    recentLeads: 15
  }
})

const recentActivities = [
  { id: 1, type: 'LEAD', title: 'Novo Lead Recebido', description: 'João Silva demonstrou interesse em Mansão Contemporânea (ID: 1024)', time: 'Há 2 horas' },
  { id: 2, type: 'PROPERTY', title: 'Imóvel Publicado', description: 'Cobertura Duplex em Ipanema foi ativada no catálogo.', time: 'Há 5 horas' },
  { id: 3, type: 'PROPERTY', title: 'Preço Atualizado', description: 'O valor da Casa em Alphaville foi reduzido em 5%.', time: 'Há 1 dia' },
  { id: 4, type: 'LEAD', title: 'Lead Contatado', description: 'O corretor Marcos mudou o status do lead Maria de Souza para Contatado.', time: 'Há 2 dias' }
]
</script>

<template>
  <div class="space-y-8">
    <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
      <div>
        <h1 class="text-h2 text-foreground">Dashboard</h1>
        <p class="text-body-sm text-text-dimmed mt-1">Bem-vindo de volta. Resumo da sua carteira de imóveis e performance.</p>
      </div>
      <UButton to="/admin/properties/create" color="primary" size="lg" icon="i-heroicons-plus" class="shadow-ambient">
        Anunciar Imóvel
      </UButton>
    </div>

    <!-- Metrics Grid -->
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
      <UCard class="bg-card border-border shadow-sm">
        <p class="text-xs font-semibold text-text-dimmed uppercase tracking-wider mb-2">VGV (Valor Geral de Vendas)</p>
        <PriceDisplay :amount="dashboard.totalMarketValue" currency="BRL" class="text-h3 text-primary" />
      </UCard>
      
      <UCard class="bg-card border-border shadow-sm">
        <p class="text-xs font-semibold text-text-dimmed uppercase tracking-wider mb-2">Imóveis Ativos</p>
        <p class="text-h3 text-foreground">{{ dashboard.activeProperties }}</p>
        <p class="text-xs text-success mt-1">+5 ativados neste mês</p>
      </UCard>

      <UCard class="bg-card border-border shadow-sm">
        <p class="text-xs font-semibold text-text-dimmed uppercase tracking-wider mb-2">Imóveis em Análise</p>
        <p class="text-h3 text-foreground">{{ dashboard.inactiveProperties }}</p>
        <p class="text-xs text-text-dimmed mt-1">Aguardando aprovação / inativos</p>
      </UCard>

      <UCard class="bg-card border-border shadow-sm relative overflow-hidden">
        <div class="relative z-10">
          <p class="text-xs font-semibold text-text-dimmed uppercase tracking-wider mb-2">Novos Leads</p>
          <p class="text-h3 text-foreground">{{ dashboard.recentLeads }}</p>
          <NuxtLink to="/admin/leads" class="text-xs text-primary font-medium hover:underline mt-1 inline-flex items-center gap-1">
            Acessar CRM <UIcon name="i-heroicons-arrow-right" class="w-3 h-3" />
          </NuxtLink>
        </div>
        <UIcon name="i-heroicons-user-group" class="w-24 h-24 absolute -right-4 -bottom-4 text-primary/5" />
      </UCard>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
      <!-- Chart/Distribution placeholder -->
      <UCard class="lg:col-span-2 bg-card border-border shadow-sm flex flex-col" :ui="{ body: 'flex-1' }">
        <h3 class="text-h4 text-foreground mb-6">Distribuição da Carteira por Tipo</h3>
        <div class="space-y-6">
          <div v-for="(count, type) in dashboard.distributionByType" :key="type">
            <div class="flex justify-between text-sm mb-2">
              <span class="text-text-dimmed">{{ t(`propertyType.${type}`) }}</span>
              <span class="font-medium text-foreground">{{ count }} imóveis</span>
            </div>
            <UProgress :model-value="count" :max="100" color="primary" />
          </div>
        </div>
      </UCard>

      <!-- Recent Activities -->
      <UCard class="bg-card border-border shadow-sm flex flex-col" :ui="{ body: 'flex-1' }">
        <h3 class="text-h4 text-foreground mb-6">Atividades Recentes</h3>
        <div class="space-y-6 flex-1">
          <div v-for="activity in recentActivities" :key="activity.id" class="flex gap-4">
            <div class="mt-1 shrink-0">
              <UIcon 
                :name="activity.type === 'LEAD' ? 'i-heroicons-user' : 'i-heroicons-home'" 
                class="w-10 h-10 p-2 rounded-full"
                :class="activity.type === 'LEAD' ? 'bg-primary/10 text-primary' : 'bg-muted text-text-dimmed'"
              />
            </div>
            <div>
              <p class="text-sm font-medium text-foreground">{{ activity.title }}</p>
              <p class="text-xs text-text-dimmed my-1 line-clamp-2 leading-relaxed">{{ activity.description }}</p>
              <p class="text-xs text-text-dimmed/50">{{ activity.time }}</p>
            </div>
          </div>
        </div>
        <template #footer>
          <UButton variant="ghost" color="neutral" block>Ver todo o histórico</UButton>
        </template>
      </UCard>
    </div>
  </div>
</template>
