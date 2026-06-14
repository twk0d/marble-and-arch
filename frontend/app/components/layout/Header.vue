<script setup lang="ts">
import type {NavigationMenuItem} from "@nuxt/ui"
import {en, pt_br} from '@nuxt/ui/locale'

const {locale, setLocale, t} = useI18n();
const auth = useAuthStore()

const props = defineProps({
  navigationItems: {
    type: Array as () => NavigationMenuItem[],
    default: undefined
  }
})

const items = computed<NavigationMenuItem[]>(() => {
  if (props.navigationItems) {
    return props.navigationItems
  }

  return [
    {
      label: t('header.navigation.rent.label'),
      to: '/search',
      children: [
        {label: t('header.navigation.rent.child.city1.label'), to: '/search?city=Teresina'},
        {label: t('header.navigation.rent.child.city2.label'), to: '/search?city=Parnaiba'},
        {label: t('header.navigation.rent.child.city3.label'), to: '/search?city=Picos'},
        {label: t('header.navigation.rent.child.city4.label'), to: '/search?city=Piripiri'},
        {label: t('header.navigation.rent.child.city5.label'), to: '/search?city=Floriano'},
        {label: t('header.navigation.rent.child.city6.label'), to: '/search?city=Barras'},
        {label: t('header.navigation.rent.child.city7.label'), to: '/search?city=Altos'},
        {label: t('header.navigation.rent.child.city8.label'), to: '/search?city=União'},
        {label: t('header.navigation.rent.child.city9.label'), to: '/search?city=Campo Maior'},
        {label: t('header.navigation.rent.child.city10.label'), to: '/search?city=Jose de Freitas'}
      ]
    }, {
      label: t('header.navigation.buy.label'),
      to: '/search',
      children: [
        {label: t('header.navigation.buy.child.city1.label'), to: '/search?city=Teresina'},
        {label: t('header.navigation.buy.child.city2.label'), to: '/search?city=Parnaiba'},
        {label: t('header.navigation.buy.child.city3.label'), to: '/search?city=Picos'},
        {label: t('header.navigation.buy.child.city4.label'), to: '/search?city=Piripiri'},
        {label: t('header.navigation.buy.child.city5.label'), to: '/search?city=Floriano'},
        {label: t('header.navigation.buy.child.city6.label'), to: '/search?city=Barras'},
        {label: t('header.navigation.buy.child.city7.label'), to: '/search?city=Altos'},
        {label: t('header.navigation.buy.child.city8.label'), to: '/search?city=União'},
        {label: t('header.navigation.buy.child.city9.label'), to: '/search?city=Campo Maior'},
        {label: t('header.navigation.buy.child.city10.label'), to: '/search?city=Jose de Freitas'}
      ]
    }, {
      label: t('header.navigation.listing.label'),
      to: '/admin/properties/create',
      children: [
        {label: t('header.navigation.listing.child.rent.label'), to: '/admin/properties/create'},
        {label: t('header.navigation.listing.child.sell.label'), to: '/admin/properties/create'}
      ]
    }, {
      label: t('header.navigation.help.label'),
      to: '#',
      children: [
        {label: t('header.navigation.help.child.customer-service.label')},
        {label: t('header.navigation.help.child.faq.label')},
        {label: t('header.navigation.help.child.about-us.label')},
        {label: t('header.navigation.help.child.security.label')},
        {label: t('header.navigation.help.child.fraud-prevention.label')}
      ]
    }
  ]
})
</script>

<template>
  <UHeader class="bg-glass-sand border-b border-border shadow-ambient transition-all duration-300">
    <template #title>
      <div class="flex items-center gap-2 hover:opacity-80 transition-opacity">
        <UIcon name="tabler:building" class="size-8 text-primary"/>
        <span class="text-h4 text-foreground tracking-tight">{{ t('header.title') }}</span>
      </div>
    </template>

    <UNavigationMenu :items="items" class="text-label-sm uppercase tracking-widest text-foreground/80 hover:text-foreground transition-colors" />

    <template #right>
      <ULocaleSelect v-model="locale" :locales="[pt_br, en]" @update:model-value="setLocale($event)"/>
      <UColorModeButton class="text-foreground hover:bg-muted rounded-lg" />
      
      <!-- Usuário não logado -->
      <template v-if="!auth.isAuthenticated">
        <UButton to="/login" variant="ghost" color="primary" icon="i-heroicons-user" class="text-label-sm px-4 py-2 hover:bg-muted transition-colors rounded-lg">
          {{ t('auth.login.submit') }}
        </UButton>
      </template>

      <!-- Usuário logado -->
      <template v-else>
        <!-- Notificações Dropdown -->
        <UPopover :popper="{ placement: 'bottom-end' }">
          <UChip text="2" size="sm" color="error">
            <UButton color="neutral" variant="ghost" icon="i-heroicons-bell" class="rounded-lg" />
          </UChip>
          <template #panel>
            <div class="p-4 w-80 max-h-96 overflow-y-auto bg-card border border-border shadow-ambient rounded-xl">
              <div class="flex items-center justify-between mb-4 pb-2 border-b border-border">
                <h4 class="text-sm font-semibold font-playfair tracking-tight">Notificações</h4>
                <UButton to="/notifications" variant="link" color="primary" size="xs">Ver todas</UButton>
              </div>
              <div class="space-y-4">
                <div class="flex gap-3">
                  <div class="w-8 h-8 rounded-full bg-primary/10 flex items-center justify-center shrink-0">
                    <UIcon name="i-heroicons-currency-dollar" class="w-4 h-4 text-primary" />
                  </div>
                  <div>
                    <p class="text-sm text-foreground font-medium">Baixa de Preço!</p>
                    <p class="text-xs text-text-dimmed line-clamp-2">A Mansão Contemporânea (Jardins) que você favoritou teve redução de 5% no valor.</p>
                    <p class="text-[10px] text-text-dimmed mt-1">Há 1 hora</p>
                  </div>
                </div>
                <div class="flex gap-3">
                  <div class="w-8 h-8 rounded-full bg-success/10 flex items-center justify-center shrink-0">
                    <UIcon name="i-heroicons-calendar-days" class="w-4 h-4 text-success" />
                  </div>
                  <div>
                    <p class="text-sm text-foreground font-medium">Visita Confirmada</p>
                    <p class="text-xs text-text-dimmed line-clamp-2">Sua visita para Cobertura Duplex amanhã às 14:00 foi confirmada.</p>
                    <p class="text-[10px] text-text-dimmed mt-1">Há 3 horas</p>
                  </div>
                </div>
              </div>
            </div>
          </template>
        </UPopover>

        <!-- Acesso ao Dashboard para Corretores/Admins -->
        <UButton 
          v-if="auth.user?.role === 'BROKER' || auth.user?.role === 'ADMIN'" 
          to="/admin/dashboard" 
          variant="solid" 
          color="primary" 
          icon="i-heroicons-chart-bar"
          class="text-label-sm shadow-ambient"
        >
          Painel
        </UButton>
        
        <!-- Botão de Sair -->
        <UButton 
          variant="ghost" 
          color="error" 
          icon="i-heroicons-arrow-right-on-rectangle"
          class="text-label-sm"
          @click="auth.logout"
        >
          Sair
        </UButton>
      </template>
    </template>

    <template #body>
      <UNavigationMenu :items="items" orientation="vertical" class="-mx-2.5 text-label-sm uppercase tracking-widest"/>
    </template>
  </UHeader>
</template>

<style scoped>
</style>
