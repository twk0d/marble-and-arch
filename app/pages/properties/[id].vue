<script setup lang="ts">
import type { PropertyDTO } from '~/types'
import PropertyGallery from '~/components/property/PropertyGallery.vue'
import PropertyStats from '~/components/property/PropertyStats.vue'
import PriceDisplay from '~/components/property/PriceDisplay.vue'

const route = useRoute()
const { t } = useI18n()
const propertyId = route.params.id as string

const { data: property, pending, error } = await useFetch<PropertyDTO>(`/api/v1/property-management/${propertyId}`, {
  key: `property-${propertyId}`,
  lazy: true
})

const breadcrumbs = computed(() => [
  { label: 'Home', to: '/' },
  { label: t('search.title'), to: '/search' },
  { label: property.value?.type ? t(`propertyType.${property.value.type}`) : '...' }
])

useSeoMeta({
  title: () => property.value ? `${t(`propertyType.${property.value.type}`)} em ${property.value.address.city} - Keystone` : 'Carregando Imóvel...',
  ogTitle: () => property.value ? `${t(`propertyType.${property.value.type}`)} em ${property.value.address.city}` : 'Keystone Imóveis',
  description: () => property.value?.details.description || 'Confira os detalhes deste imóvel exclusivo na Keystone.',
  ogDescription: () => property.value?.details.description || 'Confira os detalhes deste imóvel exclusivo na Keystone.',
  ogImage: () => property.value?.images[0]?.url || '/favicon.ico',
  twitterCard: 'summary_large_image'
})
</script>

<template>
  <UPage>
    <UPageHeader
      :title="property?.address.city ? `${t('propertyType.' + property.type)} em ${property.address.city}` : 'Carregando...'"
      :description="property ? `${property.address.street}, ${property.address.number} - ${property.address.neighborhood}, ${property.address.city}` : ''"
    >
      <template #headline>
        <UBreadcrumb :items="breadcrumbs" />
      </template>

      <template #right>
        <div v-if="property" class="flex items-center gap-2">
          <UBadge variant="subtle" color="primary">{{ t(`propertyType.${property.type}`) }}</UBadge>
          <UBadge v-if="!property.active" variant="solid" color="error">Inativo</UBadge>
        </div>
      </template>
    </UPageHeader>

    <UPageBody>
      <UPageError
        v-if="error"
        :status="error.statusCode || 500"
        title="Erro ao carregar imóvel"
        description="Não foi possível encontrar as informações deste imóvel ou ocorreu um erro no servidor."
        icon="i-heroicons-exclamation-triangle"
      >
        <template #links>
          <UButton to="/search" color="neutral" variant="ghost">
            Voltar para a busca
          </UButton>
        </template>
      </UPageError>

      <div v-else class="space-y-8">
        <!-- Image Gallery -->
        <PropertyGallery :images="property?.images" :is-loading="pending" />

        <UPageColumns>
          <!-- Main Content -->
          <div class="space-y-8">
            <!-- Technical Stats -->
            <PropertyStats :details="property?.details" :is-loading="pending" variant="grid" />

            <div v-if="pending" class="space-y-4">
              <USkeleton class="h-8 w-1/4" />
              <USkeleton class="h-32 w-full" />
            </div>
            <div v-else-if="property">
              <h3 class="text-h3 mb-4">Sobre este imóvel</h3>
              <p class="text-body-base whitespace-pre-line text-neutral-700 dark:text-neutral-300">
                {{ property.details.description || 'Sem descrição disponível.' }}
              </p>
            </div>

            <div v-if="pending" class="space-y-4">
              <USkeleton class="h-8 w-1/4" />
              <div class="flex gap-2">
                <USkeleton v-for="i in 5" :key="i" class="h-8 w-20 rounded-full" />
              </div>
            </div>
            <div v-else-if="property?.details.amenities && property.details.amenities.length > 0">
              <h3 class="text-h3 mb-4">Comodidades</h3>
              <div class="flex flex-wrap gap-2">
                <UBadge
                  v-for="amenity in property.details.amenities"
                  :key="amenity"
                  variant="subtle"
                  color="neutral"
                  size="lg"
                >
                  {{ t(`amenity.${amenity}`) }}
                </UBadge>
              </div>
            </div>
          </div>

          <!-- Sidebar / CTA -->
          <template #right>
            <div class="space-y-6">
              <UCard class="sticky top-24 border-border">
                <template v-if="pending">
                  <USkeleton class="h-4 w-1/4 mb-1" />
                  <USkeleton class="h-10 w-3/4 mb-6" />
                  <USkeleton class="h-12 w-full mb-4" />
                  <USkeleton class="h-12 w-full mb-6" />
                  <UDivider class="my-6" />
                  <div class="flex items-center gap-4">
                    <USkeleton class="h-12 w-12 rounded-full" />
                    <div class="flex-1 space-y-2">
                      <USkeleton class="h-4 w-3/4" />
                      <USkeleton class="h-3 w-1/2" />
                    </div>
                  </div>
                </template>
                <template v-else-if="property">
                  <div class="text-body-xs text-neutral-500 mb-1">Preço de Venda</div>
                  <div class="mb-6">
                    <PriceDisplay
                      :amount="property.price.amount"
                      :currency="property.price.currency"
                      size="3xl"
                    />
                  </div>

                  <UButton block size="xl" color="primary" class="mb-4">
                    {{ t('propertyCard.contactButton') }}
                  </UButton>
                  
                  <UButton block size="xl" variant="outline" color="neutral" icon="i-heroicons-share">
                    Compartilhar
                  </UButton>

                  <UDivider class="my-6" />

                  <div class="flex items-center gap-4">
                    <UAvatar src="https://i.pravatar.cc/150?u=broker" size="lg" />
                    <div>
                      <div class="text-body-bold text-sm">Corretor Responsável</div>
                      <div class="text-body-xs text-neutral-500">Agência Keystone</div>
                    </div>
                  </div>
                </template>
              </UCard>
            </div>
          </template>
        </UPageColumns>
      </div>
    </UPageBody>
  </UPage>
</template>
