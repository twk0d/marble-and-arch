<script setup lang="ts">
import type { PropertyDTO } from '~/types'
import PropertyStats from '~/components/property/PropertyStats.vue'
import AgentContactForm from '~/components/property/AgentContactForm.vue'
import PriceDisplay from '~/components/property/PriceDisplay.vue'
import ScheduleVisitModal from '~/components/property/ScheduleVisitModal.vue'

const route = useRoute()
const { t } = useI18n()

// Mocking the property data until the API is integrated
const mockProperty: PropertyDTO = {
  id: route.params.id as string,
  active: true,
  type: 'HOUSE' as any,
  address: {
    street: 'Rua das Oliveiras',
    number: '123',
    complement: 'Condomínio Alpha',
    neighborhood: 'Jardins',
    city: 'São Paulo',
    state: 'SP',
    zipCode: '01400-000'
  },
  price: {
    amount: 14500000,
    currency: 'BRL'
  },
  details: {
    bedrooms: 5,
    suites: 5,
    bathrooms: 7,
    parkingSpaces: 6,
    totalAreaValue: 850,
    builtAreaValue: 600,
    description: 'Mansão contemporânea com projeto assinado, pé direito duplo de 6 metros na sala principal, automação completa e acabamentos em mármore travertino. Área de lazer espetacular com piscina borda infinita e espaço gourmet.',
    hasGarage: true,
    hasPool: true,
  },
  images: [
    { id: '1', url: 'https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=1600&q=80', description: 'Fachada', active: true },
    { id: '2', url: 'https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=800&q=80', description: 'Sala de Estar', active: true },
    { id: '3', url: 'https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&w=800&q=80', description: 'Piscina', active: true },
    { id: '4', url: 'https://images.unsplash.com/photo-1613977257363-707ba9348227?auto=format&fit=crop&w=800&q=80', description: 'Cozinha', active: true },
  ]
}

const { data: response, pending, error } = useFetch<any>(`/api/v1/properties/${route.params.id}`, {
  lazy: true,
  server: false
})

const property = computed<PropertyDTO>(() => {
  if (response.value && response.value.success && response.value.data) {
    const p = response.value.data;
    return {
      id: p.id,
      active: p.active ?? true,
      type: p.type || 'HOUSE',
      address: p.address || mockProperty.address,
      price: p.price || mockProperty.price,
      details: p.details || mockProperty.details,
      images: p.images?.length > 0 ? p.images : mockProperty.images
    } as PropertyDTO
  }
  return mockProperty
})

const activeImage = ref(property.value.images[0]?.url)
watch(() => property.value, (newVal) => {
  if (newVal.images && newVal.images.length > 0) {
    activeImage.value = newVal.images[0].url
  }
}, { immediate: true })

const isScheduleModalOpen = ref(false)

</script>

<template>
  <div class="pb-24">
    <!-- Breadcrumb (Clean) -->
    <div class="py-6 border-b border-border">
      <div class="max-w-[1440px] mx-auto px-4 sm:px-6 lg:px-8">
        <UBreadcrumb
          :links="[
            { label: 'Home', to: '/' },
            { label: 'Catálogo', to: '/search' },
            { label: t(`propertyType.${property.type}`) }
          ]"
          class="text-label-sm"
        />
      </div>
    </div>

    <!-- Immersive Gallery Hero -->
    <div class="w-full max-w-[1440px] mx-auto px-4 sm:px-6 lg:px-8 mt-8 mb-12">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4 h-[500px] lg:h-[650px]">
        <!-- Main Image -->
        <div class="md:col-span-3 h-full rounded-2xl overflow-hidden relative group cursor-pointer border border-border">
          <NuxtImg 
            :src="activeImage" 
            class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105"
          />
          <div class="absolute inset-0 bg-black/10 group-hover:bg-transparent transition-colors" />
          <UBadge class="absolute top-6 left-6 shadow-ambient px-4 py-1.5 bg-glass-sand border border-white/20 text-label-sm uppercase" variant="solid">
            Código: {{ property.id.split('-')[0] || property.id }}
          </UBadge>
        </div>
        
        <!-- Side Images -->
        <div class="hidden md:flex flex-col gap-4 h-full">
          <div 
            v-for="(img, idx) in property.images.slice(1, 3)" 
            :key="img.id"
            class="h-1/2 rounded-2xl overflow-hidden relative cursor-pointer group border border-border"
            @click="activeImage = img.url"
          >
            <NuxtImg 
              :src="img.url" 
              class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-105"
            />
            <!-- Overlay to view all photos on the last image -->
            <div v-if="idx === 1" class="absolute inset-0 bg-black/40 flex items-center justify-center group-hover:bg-black/50 transition-colors">
              <span class="text-white text-h4">+{{ property.images.length - 3 }} Fotos</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Main Content & Sidebar -->
    <div class="max-w-[1440px] mx-auto px-4 sm:px-6 lg:px-8 grid grid-cols-1 lg:grid-cols-3 gap-12">
      
      <!-- Left Column: Details -->
      <div class="lg:col-span-2 space-y-12">
        
        <!-- Header Info -->
        <div class="pb-10 border-b border-border">
          <div class="mb-4">
            <h1 class="text-h1 text-foreground mb-2">Mansão Contemporânea Exclusiva</h1>
            <p class="text-body-lg text-text-dimmed flex items-center gap-2">
              <UIcon name="i-heroicons-map-pin" class="w-5 h-5 text-primary/80" />
              {{ property.address.neighborhood }}, {{ property.address.city }} - {{ property.address.state }}
            </p>
          </div>
          
          <div class="mt-8">
            <PropertyStats :property="property" />
          </div>
        </div>

        <!-- Description -->
        <section>
          <h2 class="text-h3 text-foreground mb-6">Sobre o Imóvel</h2>
          <div class="prose prose-neutral dark:prose-invert max-w-none text-body-lg text-text-dimmed leading-relaxed">
            <p>{{ property.details.description }}</p>
          </div>
        </section>

        <!-- Details Grid -->
        <section>
          <h2 class="text-h3 text-foreground mb-6">Detalhes</h2>
          <div class="grid grid-cols-2 sm:grid-cols-3 gap-y-6 gap-x-4 p-8 rounded-2xl bg-muted border border-border">
            <div>
              <span class="block text-label-sm text-text-dimmed mb-1">Área Total</span>
              <span class="text-h5 text-foreground">{{ property.details.totalAreaValue }} m²</span>
            </div>
            <div>
              <span class="block text-label-sm text-text-dimmed mb-1">Área Útil</span>
              <span class="text-h5 text-foreground">{{ property.details.builtAreaValue }} m²</span>
            </div>
            <div>
              <span class="block text-label-sm text-text-dimmed mb-1">Ano de Construção</span>
              <span class="text-h5 text-foreground">{{ property.details.yearBuilt || 'N/A' }}</span>
            </div>
            <div>
              <span class="block text-label-sm text-text-dimmed mb-1">Banheiros</span>
              <span class="text-h5 text-foreground">{{ property.details.bathrooms }}</span>
            </div>
            <div>
              <span class="block text-label-sm text-text-dimmed mb-1">Tipo de Imóvel</span>
              <span class="text-h5 text-foreground">{{ t(`propertyType.${property.type}`) }}</span>
            </div>
          </div>
        </section>

      </div>

      <!-- Right Column: Sidebar -->
      <div class="lg:col-span-1 relative">
        <div class="sticky top-24 space-y-6">
          
          <!-- Price Card -->
          <UCard class="bg-card border border-border shadow-sm">
            <p class="text-label-sm text-text-dimmed mb-2">Valor de Venda</p>
            <PriceDisplay :amount="property.price.amount" :currency="property.price.currency" class="text-h2 text-foreground" />
            
            <div class="mt-6 pt-6 border-t border-border grid grid-cols-2 gap-4">
              <div>
                <p class="text-xs text-text-dimmed">Condomínio</p>
                <p class="text-body-base font-semibold">R$ 3.500</p>
              </div>
              <div>
                <p class="text-xs text-text-dimmed">IPTU (mensal)</p>
                <p class="text-body-base font-semibold">R$ 1.200</p>
              </div>
            </div>
          </UCard>

          <!-- Contact Form -->
          <AgentContactForm :propertyId="property.id" />

          <UButton block color="neutral" variant="outline" size="xl" class="mt-4" @click="isScheduleModalOpen = true">
            Agendar Visita Presencial
          </UButton>
        </div>
      </div>
    </div>

    <!-- Modals -->
    <ScheduleVisitModal v-model:open="isScheduleModalOpen" @scheduled="useToast().add({ title: 'Sucesso', description: 'Visita agendada com sucesso!', color: 'success' })" />
  </div>
</template>
