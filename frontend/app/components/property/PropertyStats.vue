<script setup lang="ts">
import type { PropertyDTO } from '~/types';

const props = defineProps<{
  property: PropertyDTO | any; // allow any for flexibility if needed
}>();

const { t } = useI18n();

const bedrooms = computed(() => props.property?.details?.bedrooms || 0);
const suites = computed(() => props.property?.details?.suites || 0);
const parkingSpaces = computed(() => props.property?.details?.parkingSpaces || 0);
const totalArea = computed(() => props.property?.details?.totalAreaValue || 0);
</script>

<template>
  <div class="flex flex-wrap items-center gap-4 sm:gap-8 text-body-base text-text-dimmed">
    <div v-if="bedrooms > 0" class="flex items-center gap-2">
      <UIcon name="i-heroicons-home" class="w-5 h-5 text-primary" />
      <span class="font-medium text-foreground">{{ bedrooms }} <span class="text-text-dimmed font-normal">Quartos</span></span>
    </div>
    <div v-if="suites > 0" class="flex items-center gap-2">
      <UIcon name="i-heroicons-sparkles" class="w-5 h-5 text-primary" />
      <span class="font-medium text-foreground">{{ suites }} <span class="text-text-dimmed font-normal">Suítes</span></span>
    </div>
    <div v-if="totalArea > 0" class="flex items-center gap-2">
      <UIcon name="i-heroicons-view-columns" class="w-5 h-5 text-primary" />
      <span class="font-medium text-foreground">{{ totalArea }} <span class="text-text-dimmed font-normal">m²</span></span>
    </div>
    <div v-if="parkingSpaces > 0" class="flex items-center gap-2">
      <UIcon name="i-lucide-car" class="w-5 h-5 text-primary" />
      <span class="font-medium text-foreground">{{ parkingSpaces }} <span class="text-text-dimmed font-normal">Vagas</span></span>
    </div>
  </div>
</template>
