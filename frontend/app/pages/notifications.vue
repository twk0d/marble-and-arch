<script setup lang="ts">
definePageMeta({
  layout: 'default'
})

const { t } = useI18n()

const notifications = ref([
  {
    id: 1,
    type: 'price_drop',
    icon: 'i-heroicons-currency-dollar',
    color: 'primary',
    title: 'Baixa de Preço: Mansão Contemporânea',
    description: 'A propriedade que você favoritou nos Jardins teve uma redução de 5% no valor anunciado.',
    date: 'Hoje, 10:30',
    read: false,
    actionLabel: 'Ver Imóvel',
    actionUrl: '/properties/1024'
  },
  {
    id: 2,
    type: 'visit_confirmed',
    icon: 'i-heroicons-calendar-days',
    color: 'success',
    title: 'Visita Confirmada: Cobertura Duplex',
    description: 'O corretor Marcos confirmou sua visita amanhã às 14:00. O endereço completo foi enviado para seu e-mail.',
    date: 'Hoje, 08:15',
    read: false,
    actionLabel: 'Meus Agendamentos',
    actionUrl: '/profile'
  },
  {
    id: 3,
    type: 'new_match',
    icon: 'i-heroicons-sparkles',
    color: 'warning',
    title: 'Novo Imóvel no seu Radar',
    description: 'Uma nova Cobertura em Higienópolis acabou de entrar no catálogo que condiz com suas buscas recentes.',
    date: 'Ontem',
    read: true,
    actionLabel: 'Ver Imóvel',
    actionUrl: '/properties/2099'
  }
])

function markAllAsRead() {
  notifications.value.forEach(n => n.read = true)
}
</script>

<template>
  <div class="max-w-4xl mx-auto px-4 sm:px-6 py-12 min-h-[60vh]">
    <div class="flex flex-col sm:flex-row sm:items-end justify-between mb-8 pb-4 border-b border-border">
      <div>
        <h1 class="text-h2 text-foreground font-playfair mb-1">Central de Notificações</h1>
        <p class="text-body-sm text-text-dimmed">Acompanhe alertas importantes sobre seus imóveis favoritos e agendamentos.</p>
      </div>
      <UButton variant="ghost" color="neutral" icon="i-heroicons-check-circle" class="mt-4 sm:mt-0 self-start" @click="markAllAsRead">
        Marcar todas como lidas
      </UButton>
    </div>

    <div class="space-y-4">
      <div 
        v-for="notif in notifications" 
        :key="notif.id"
        class="flex gap-4 p-4 sm:p-6 rounded-2xl border transition-colors duration-300"
        :class="notif.read ? 'bg-transparent border-border opacity-70' : 'bg-card border-primary/20 shadow-sm'"
      >
        <div class="shrink-0 mt-1">
          <div 
            class="w-10 h-10 rounded-full flex items-center justify-center"
            :class="[
              notif.color === 'primary' ? 'bg-primary/10 text-primary' : 
              notif.color === 'success' ? 'bg-success/10 text-success' : 
              'bg-amber-500/10 text-amber-500'
            ]"
          >
            <UIcon :name="notif.icon" class="w-5 h-5" />
          </div>
        </div>
        <div class="flex-1">
          <div class="flex justify-between items-start gap-2">
            <h4 class="text-body-base font-semibold text-foreground">{{ notif.title }}</h4>
            <span class="text-xs text-text-dimmed whitespace-nowrap">{{ notif.date }}</span>
          </div>
          <p class="text-body-sm text-text-dimmed mt-1">{{ notif.description }}</p>
          <div class="mt-3" v-if="notif.actionUrl">
            <UButton :to="notif.actionUrl" variant="link" :color="notif.color as any" size="sm" class="px-0">
              {{ notif.actionLabel }} &rarr;
            </UButton>
          </div>
        </div>
        <div class="shrink-0 flex items-center" v-if="!notif.read">
          <div class="w-2.5 h-2.5 bg-primary rounded-full"></div>
        </div>
      </div>
    </div>
    
    <div v-if="notifications.length === 0" class="text-center py-20">
      <UIcon name="i-heroicons-bell-slash" class="w-16 h-16 text-text-dimmed/30 mx-auto mb-4" />
      <h3 class="text-h4 text-foreground mb-2">Sem notificações</h3>
      <p class="text-body-sm text-text-dimmed">Você está em dia com todos os seus alertas.</p>
    </div>
  </div>
</template>
