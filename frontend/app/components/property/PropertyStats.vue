<script setup lang="ts">
import type { PropertyDetailsDTO } from '~/types/property';

const { t } = useI18n();

withDefaults(defineProps<{
  details?: PropertyDetailsDTO;
  variant?: 'grid' | 'list';
  isLoading?: boolean;
}>(), {
  variant: 'grid',
  isLoading: false
});
</script>

<template>
  <div v-if="isLoading" :class="variant === 'grid' ? 'grid grid-cols-2 md:grid-cols-4 gap-4' : 'flex flex-wrap gap-4'">
    <template v-if="variant === 'grid'">
      <UCard v-for="i in 4" :key="i" class="text-center py-4 border-border">
        <USkeleton class="w-6 h-6 mx-auto mb-2" />
        <USkeleton class="h-4 w-1/2 mx-auto mb-1" />
        <USkeleton class="h-3 w-1/3 mx-auto" />
      </UCard>
    </template>
    <template v-else>
      <USkeleton v-for="i in 3" :key="i" class="h-4 w-20 rounded" />
    </template>
  </div>

  <template v-else-if="details">
    <div v-if="variant === 'grid'" class="grid grid-cols-2 md:grid-cols-4 gap-4">
      <UCard v-if="details.bedrooms" class="text-center py-4 border-border">
        <UIcon name="i-heroicons-home" class="w-6 h-6 mx-auto mb-2 text-primary" />
        <div class="text-body-bold">{{ details.bedrooms }}</div>
        <div class="text-body-xs text-neutral-500">{{ t('propertyCard.bedrooms') }}</div>
      </UCard>
      <UCard v-if="details.bathrooms" class="text-center py-4 border-border">
        <UIcon name="i-heroicons-sparkles" class="w-6 h-6 mx-auto mb-2 text-primary" />
        <div class="text-body-bold">{{ details.bathrooms }}</div>
        <div class="text-body-xs text-neutral-500">{{ t('propertyCard.bathrooms') }}</div>
      </UCard>
      <UCard v-if="details.area" class="text-center py-4 border-border">
        <UIcon name="i-heroicons-arrows-pointing-out" class="w-6 h-6 mx-auto mb-2 text-primary" />
        <div class="text-body-bold">{{ details.area }}</div>
        <div class="text-body-xs text-neutral-500">{{ t('propertyCard.areaUnit') }}</div>
      </UCard>
      <UCard v-if="details.parkingSpaces" class="text-center py-4 border-border">
        <UIcon name="i-heroicons-truck" class="w-6 h-6 mx-auto mb-2 text-primary" />
        <div class="text-body-bold">{{ details.parkingSpaces }}</div>
        <div class="text-body-xs text-neutral-500">Vagas</div>
      </UCard>
    </div>
    
    <div v-else class="flex flex-wrap gap-4 text-sm text-neutral-600 dark:text-neutral-400">
      <div v-if="details.bedrooms" class="flex items-center gap-1">
        <UIcon name="i-heroicons-home" class="w-4 h-4" />
        <span>{{ details.bedrooms }} {{ t('propertyCard.bedrooms') }}</span>
      </div>
      <div v-if="details.bathrooms" class="flex items-center gap-1">
        <UIcon name="i-heroicons-sparkles" class="w-4 h-4" />
        <span>{{ details.bathrooms }} {{ t('propertyCard.bathrooms') }}</span>
      </div>
      <div v-if="details.area" class="flex items-center gap-1">
        <UIcon name="i-heroicons-arrows-pointing-out" class="w-4 h-4" />
        <span>{{ details.area }} {{ t('propertyCard.areaUnit') }}</span>
      </div>
    </div>
  </template>
</template>
