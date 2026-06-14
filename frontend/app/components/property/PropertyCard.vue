<script setup lang="ts">
import type { PropertySummaryDTO } from '~/types';
import PriceDisplay from '~/components/property/PriceDisplay.vue';

const { t } = useI18n();

const props = defineProps<{
  property: PropertySummaryDTO;
  disableNavigation?: boolean;
}>();

const emit = defineEmits(['select']);

function handleClick(e: MouseEvent) {
  if (props.disableNavigation) {
    e.preventDefault();
  }
  emit('select', props.property);
}
</script>

<template>
  <UPageCard
    :to="disableNavigation ? undefined : `/properties/${property.id}`"
    class="mb-2 items-start overflow-hidden group border-transparent bg-transparent hover:bg-card cursor-pointer hover-card-lift transition-all duration-300"
    :ui="{ body: 'p-4 sm:p-5', header: 'p-0 sm:p-0' }"
    @click="handleClick"
  >
    <template #header>
      <div class="aspect-video relative overflow-hidden bg-muted flex items-center justify-center">
        <NuxtImg
          v-if="property.mainImageUrl"
          :src="property.mainImageUrl"
          :alt="property.id"
          width="400"
          height="225"
          format="webp"
          loading="lazy"
          class="w-full h-full object-cover transition-transform duration-700 ease-out group-hover:scale-105"
        />
        <div v-else class="w-full h-full bg-grid-pattern opacity-40 flex items-center justify-center">
          <UIcon name="i-lucide-home" class="w-12 h-12 text-neutral-400" />
        </div>
        <!-- Elegant subtle overlay to blend shadows and increase contrast -->
        <div class="absolute inset-0 bg-gradient-to-t from-black/20 via-transparent to-transparent opacity-80 pointer-events-none" />
        <UBadge class="absolute top-3 right-3 shadow-sm" variant="solid" color="primary">
          {{ t(`propertyType.${property.type}`) }}
        </UBadge>
      </div>
    </template>

    <template #title>
      <PriceDisplay
        :amount="property.priceAmount"
        :currency="property.priceCurrency"
        size="lg"
        class="mt-1"
      />
    </template>

    <template #description>
      <div class="flex items-center gap-1.5 text-body-sm text-neutral-600 dark:text-neutral-400 mt-1 mb-1">
        <UIcon name="i-heroicons-map-pin" class="w-4 h-4 text-primary/70" />
        {{ property.city }}, {{ property.state }}
      </div>
    </template>
  </UPageCard>
</template>
