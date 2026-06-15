<script setup lang="ts">
import PropertyCard from '~/components/property/PropertyCard.vue'

definePageMeta({
  layout: 'default'
})

const { t } = useI18n()

// Mock user data
const user = ref({
  name: 'Carlos Oliveira',
  email: 'carlos.oliveira@exemplo.com',
  phone: '(11) 99999-9999',
  avatar: 'https://i.pravatar.cc/150?u=a042581f4e29026024d'
})

// Mock favorite properties
const favoriteProperties = ref([
  {
    id: '1024',
    title: 'Mansão Contemporânea',
    city: 'São Paulo',
    state: 'SP',
    priceAmount: 8900000,
    priceCurrency: 'BRL',
    mainImageUrl: 'https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=800&q=80',
    type: 'HOUSE'
  },
  {
    id: '1080',
    title: 'Cobertura Duplex',
    city: 'Rio de Janeiro',
    state: 'RJ',
    priceAmount: 12500000,
    priceCurrency: 'BRL',
    mainImageUrl: 'https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&w=800&q=80',
    type: 'PENTHOUSE'
  }
])

const activeTab = ref('favorites')

const tabs = [
  { key: 'favorites', label: 'Meus Favoritos', icon: 'i-heroicons-heart' },
  { key: 'settings', label: 'Dados Pessoais', icon: 'i-heroicons-user' },
  { key: 'visits', label: 'Visitas Agendadas', icon: 'i-heroicons-calendar-days' }
]
</script>

<template>
  <div class="max-w-[1440px] mx-auto px-4 sm:px-6 lg:px-8 py-12">
    <!-- Header Perfil -->
    <div class="flex flex-col md:flex-row items-start md:items-center gap-8 mb-12 pb-8 border-b border-border">
      <UAvatar :src="user.avatar" :alt="user.name" size="3xl" class="border-2 border-border shadow-ambient" />
      <div>
        <h1 class="text-h2 text-foreground">{{ user.name }}</h1>
        <p class="text-body-base text-text-dimmed mt-1">{{ user.email }} • Cliente Premium</p>
      </div>
    </div>

    <div class="flex flex-col lg:flex-row gap-12">
      <!-- Sidebar de Navegação Interna -->
      <div class="w-full lg:w-64 shrink-0">
        <UVerticalNavigation :links="tabs.map(tab => ({
          label: tab.label,
          icon: tab.icon,
          click: () => activeTab = tab.key,
          active: activeTab === tab.key
        }))" 
        :ui="{ 
          wrapper: 'space-y-1',
          base: 'group block border-l-2 border-transparent hover:border-primary px-4 py-3 text-sm font-medium transition-colors',
          active: 'text-primary border-primary bg-primary/5',
          inactive: 'text-text-dimmed hover:text-foreground hover:bg-muted'
        }" />
        
        <UButton variant="ghost" color="neutral" icon="i-heroicons-arrow-right-on-rectangle" class="w-full justify-start mt-8 text-text-dimmed hover:text-error">
          Sair da Conta
        </UButton>
      </div>

      <!-- Conteúdo das Abas -->
      <div class="flex-1">
        <!-- Meus Favoritos -->
        <div v-if="activeTab === 'favorites'">
          <div class="mb-8">
            <h2 class="text-h3 text-foreground mb-2">Imóveis Favoritados</h2>
            <p class="text-body-sm text-text-dimmed">Sua curadoria pessoal de imóveis de luxo.</p>
          </div>
          
          <div v-if="favoriteProperties.length > 0" class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            <div v-for="prop in favoriteProperties" :key="prop.id" class="relative group">
              <PropertyCard :property="prop" class="h-full" />
              <!-- Botão de Desfavoritar Flutuante -->
              <UButton 
                icon="i-heroicons-heart-solid" 
                color="white" 
                variant="solid" 
                class="absolute top-4 right-4 rounded-full text-primary shadow-ambient opacity-0 group-hover:opacity-100 transition-opacity z-10" 
              />
            </div>
          </div>
          
          <UEmpty v-else title="Nenhum imóvel favoritado" description="Explore nosso catálogo para encontrar propriedades exclusivas." icon="i-heroicons-heart" />
        </div>

        <!-- Dados Pessoais -->
        <div v-else-if="activeTab === 'settings'">
          <div class="mb-8">
            <h2 class="text-h3 text-foreground mb-2">Dados Pessoais</h2>
            <p class="text-body-sm text-text-dimmed">Gerencie as informações da sua conta.</p>
          </div>
          
          <UCard class="bg-card border-border shadow-sm max-w-2xl" :ui="{ body: 'p-6 sm:p-8' }">
            <UForm :state="user" class="space-y-6">
              <UFormField label="Nome Completo">
                <UInput v-model="user.name" size="lg" />
              </UFormField>
              <UFormField label="E-mail">
                <UInput v-model="user.email" disabled size="lg" />
              </UFormField>
              <UFormField label="Telefone">
                <UInput v-model="user.phone" size="lg" />
              </UFormField>
              <div class="pt-4 mt-6 border-t border-border">
                <UButton color="primary" size="lg" class="shadow-ambient">Salvar Alterações</UButton>
              </div>
            </UForm>
          </UCard>
        </div>
        
        <!-- Visitas Agendadas -->
        <div v-else-if="activeTab === 'visits'">
          <div class="mb-8">
            <h2 class="text-h3 text-foreground mb-2">Próximas Visitas</h2>
            <p class="text-body-sm text-text-dimmed">Acompanhe seus agendamentos presenciais.</p>
          </div>
          
          <UCard class="bg-card border-border shadow-sm">
            <div class="flex flex-col sm:flex-row gap-6 items-start sm:items-center">
              <div class="w-16 h-16 rounded-xl bg-primary/10 border border-primary/20 flex flex-col items-center justify-center text-primary shrink-0">
                <span class="text-xs font-semibold uppercase">Nov</span>
                <span class="text-xl font-bold">15</span>
              </div>
              <div class="flex-1">
                <h4 class="text-h5 text-foreground mb-1">Mansão Contemporânea (Jardins)</h4>
                <p class="text-body-sm text-text-dimmed flex items-center gap-2">
                  <UIcon name="i-heroicons-clock" class="w-4 h-4 text-primary/60" /> Sexta-feira, às 14:30
                </p>
                <p class="text-body-sm text-text-dimmed flex items-center gap-2 mt-1">
                  <UIcon name="i-heroicons-user" class="w-4 h-4 text-primary/60" /> Corretor: Marcos Aurélio
                </p>
              </div>
              <div class="flex flex-col sm:items-end gap-2">
                <UBadge color="primary" variant="subtle" size="sm">Confirmada</UBadge>
                <UButton variant="link" color="neutral" size="sm">Reagendar</UButton>
              </div>
            </div>
          </UCard>
        </div>
      </div>
    </div>
  </div>
</template>
