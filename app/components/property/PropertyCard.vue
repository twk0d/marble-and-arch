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
    class="mb-2 items-start overflow-hidden group border-border cursor-pointer"
    :ui="{ body: 'p-4' }"
    @click="handleClick"
  >
    <template #header>
      <div class="aspect-video relative overflow-hidden bg-muted">
        <NuxtImg
          v-if="property.mainImageUrl"
          :src="property.mainImageUrl"
          :alt="property.id"
          width="400"
          height="225"
          format="webp"
          loading="lazy"
          class="w-full h-full object-cover transition-transform duration-300 group-hover:scale-105"
        />
        <div v-else class="w-full h-full flex items-center justify-center text-6xl opacity-30">🏠</div>
        <UBadge class="absolute top-2 right-2" variant="solid" color="primary">
          {{ t(`propertyType.${property.type}`) }}
        </UBadge>
      </div>
    </template>

    <template #title>
      <PriceDisplay
        :amount="property.priceAmount"
        :currency="property.priceCurrency"
        size="lg"
      />
    </template>

    <template #description>
      <div class="flex items-center gap-1 text-body-sm text-neutral-600 dark:text-neutral-400 mb-2">
        <UIcon name="i-heroicons-map-pin" class="w-4 h-4" />
        {{ property.city }}, {{ property.state }}
      </div>
    </template>
  </UPageCard>
</template>
