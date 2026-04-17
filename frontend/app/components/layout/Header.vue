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
      to: '/help',
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
  <UHeader class="border-b border-border">
    <template #title>
      <div class="flex items-center gap-2">
        <UIcon name="tabler:building" class="size-8 text-primary"/>
        <span class="text-h5">{{ t('header.title') }}</span>
      </div>
    </template>

    <UNavigationMenu :items="items" />

    <template #right>
      <ULocaleSelect v-model="locale" :locales="[pt_br, en]" @update:model-value="setLocale($event)"/>
      <UColorModeButton/>
      
      <!-- Usuário não logado -->
      <template v-if="!auth.isAuthenticated">
        <UButton to="/login" variant="ghost" color="neutral" icon="i-heroicons-user" class="font-inter">
          {{ t('auth.login.submit') }}
        </UButton>
      </template>

      <!-- Usuário logado -->
      <template v-else>
        <!-- Acesso ao Dashboard para Corretores/Admins -->
        <UButton 
          v-if="auth.user?.role === 'BROKER' || auth.user?.role === 'ADMIN'" 
          to="/admin/dashboard" 
          variant="ghost" 
          color="primary" 
          icon="i-heroicons-chart-bar"
          class="font-inter"
        >
          Painel
        </UButton>
        
        <!-- Botão de Sair -->
        <UButton 
          variant="ghost" 
          color="error" 
          icon="i-heroicons-arrow-right-on-rectangle"
          class="font-inter"
          @click="auth.logout"
        >
          Sair
        </UButton>
      </template>
    </template>

    <template #body>
      <UNavigationMenu :items="items" orientation="vertical" class="-mx-2.5"/>
    </template>
  </UHeader>
</template>

<style scoped>
</style>