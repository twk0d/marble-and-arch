<script setup lang="ts">
import type { PropertySummaryDTO } from '~/types';
import PropertyCard from '~/components/property/PropertyCard.vue';

const props = withDefaults(defineProps<{
  properties: PropertySummaryDTO[];
  totalElements?: number;
  totalPages?: number;
  currentPage?: number;
  isLoading?: boolean;
  emptyTitle?: string;
  emptyDescription?: string;
  emptyIcon?: string;
}>(), {
  totalElements: 0,
  totalPages: 0,
  currentPage: 0,
  isLoading: false,
  emptyTitle: 'Nenhuma propriedade encontrada',
  emptyIcon: 'i-heroicons-home'
});

const emit = defineEmits(['page-change', 'reset-filters', 'select']);

function onPageChange(page: number) {
  emit('page-change', page);
}

function onSelect(property: PropertySummaryDTO) {
  emit('select', property);
}

function onReset() {
  emit('reset-filters');
}
</script>

<template>
  <div>
    <div v-if="isLoading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <USkeleton v-for="i in 6" :key="i" class="h-80 w-full" />
    </div>

    <template v-else>
      <UPageGrid v-if="properties.length > 0">
        <PropertyCard
          v-for="property in properties"
          :key="property.id"
          :property="property"
          :disable-navigation="!!$attrs.onSelect"
          @select="onSelect"
        />
      </UPageGrid>

      <UEmpty
        v-else
        :title="emptyTitle"
        :description="emptyDescription"
        :icon="emptyIcon"
        class="py-20"
      >
        <template #actions>
          <UButton
            v-if="emptyTitle === 'Nenhuma propriedade encontrada'"
            variant="link"
            @click="onReset"
          >
            Limpar Filtros
          </UButton>
          <slot name="empty-actions" />
        </template>
      </UEmpty>

      <div v-if="totalPages > 1" class="flex justify-center mt-12">
        <UPagination
          :model-value="currentPage + 1"
          :total="totalElements"
          :page-count="12"
          @update:model-value="onPageChange"
        />
      </div>
    </template>
  </div>
</template>
