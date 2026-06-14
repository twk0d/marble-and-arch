<script setup lang="ts">
import type { PropertySummaryDTO } from '~/types';
import PriceDisplay from '~/components/property/PriceDisplay.vue';
import PropertyStats from '~/components/property/PropertyStats.vue';

const props = defineProps<{
  property: PropertySummaryDTO | null;
}>();

const isOpen = defineModel<boolean>({ default: false });
const emit = defineEmits(['view-full']);

const { t } = useI18n();

// Mock details for the quick view since SummaryDTO doesn't have them all
const mockDetails = {
  bedrooms: 3,
  bathrooms: 2,
  parkingSpaces: 2,
  area: 120,
  description: 'Um belíssimo imóvel recém-adicionado ao nosso catálogo. Perfeito para quem busca conforto e qualidade de vida em uma localização privilegiada. Possui acabamentos de primeira linha e excelente iluminação natural.'
};
</script>

<template>
  <UModal v-model="isOpen" :ui="{ width: 'sm:max-w-4xl' }">
    <template #content>
      <UCard v-if="property" :ui="{ ring: '', divide: 'divide-y divide-border', body: 'p-0', header: 'p-4 sm:px-6' }">
        <template #header>
          <div class="flex items-center justify-between">
            <h3 class="text-h4 text-foreground">
              {{ t(`propertyType.${property.type}`) }} em {{ property.city }}
            </h3>
            <UButton color="neutral" variant="ghost" icon="i-heroicons-x-mark-20-solid" class="-my-1" @click="isOpen = false" />
          </div>
        </template>

        <div class="grid grid-cols-1 md:grid-cols-2">
          <!-- Left: Image -->
          <div class="aspect-square md:aspect-auto md:h-[500px] relative bg-muted">
            <NuxtImg
              v-if="property.mainImageUrl"
              :src="property.mainImageUrl"
              class="w-full h-full object-cover"
            />
            <div v-else class="w-full h-full flex items-center justify-center text-6xl opacity-30">🏠</div>
            <UBadge class="absolute top-4 left-4" variant="solid" color="primary" size="lg">
              {{ t(`propertyType.${property.type}`) }}
            </UBadge>
          </div>

          <!-- Right: Details -->
          <div class="p-6 space-y-6 flex flex-col justify-between md:h-[500px] overflow-y-auto">
            <div>
              <PriceDisplay :amount="property.priceAmount" :currency="property.priceCurrency" size="2xl" class="mb-1 block" />
              <div class="flex items-center gap-1 text-body-sm text-neutral-500 mb-6">
                <UIcon name="i-heroicons-map-pin" class="w-4 h-4" />
                {{ property.city }} - {{ property.state }}
              </div>

              <PropertyStats :details="mockDetails" variant="grid" class="mb-6" />
              
              <p class="text-body-sm text-neutral-600 dark:text-neutral-400 line-clamp-4">
                {{ mockDetails.description }}
              </p>
            </div>

            <div class="flex flex-col gap-3 pt-6 mt-auto">
              <UButton size="xl" color="primary" block @click="emit('view-full')">
                Ver Detalhes Completos
              </UButton>
              <UButton size="xl" color="neutral" variant="outline" block>
                {{ t('propertyCard.contactButton') }}
              </UButton>
            </div>
          </div>
        </div>
      </UCard>
    </template>
  </UModal>
</template>
