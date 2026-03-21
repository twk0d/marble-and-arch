<script setup lang="ts">
import { useSearchStore } from '~/stores/search'
import { PropertyType, Amenity } from '~/types'

const { t } = useI18n()
const searchStore = useSearchStore()

const propertyTypeOptions = computed(() => [
  { label: 'Todos os tipos', value: undefined },
  ...Object.values(PropertyType).map(type => ({
    label: t(`propertyType.${type}`),
    value: type
  }))
])

const amenityOptions = Object.values(Amenity).map(amenity => ({
  label: t(`amenity.${amenity}`),
  value: amenity
}))

const emit = defineEmits(['search'])

// Reactive search with debounce
let timeoutId: ReturnType<typeof setTimeout>
function debouncedSearch() {
  clearTimeout(timeoutId)
  timeoutId = setTimeout(() => {
    emit('search')
  }, 500) // 500ms debounce
}

// Watch specific filters to trigger auto-search (excluding text inputs that are handled by @input)
watch(
  () => [
    searchStore.filters.type,
    searchStore.filters.amenities
  ],
  () => {
    debouncedSearch()
  },
  { deep: true }
)

function handleReset() {
  searchStore.resetFilters()
  emit('search')
}

const showAdvanced = ref(false)
</script>

<template>
  <UCard class="bg-muted border-border" :ui="{ body: 'p-6' }">
    <div class="space-y-6">
      <!-- Main Filters -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <!-- Localização -->
        <UFormField :label="t('search.placeholder')" class="lg:col-span-2">
          <div class="flex gap-2">
            <UInput 
              v-model="searchStore.filters.city" 
              icon="i-heroicons-map-pin" 
              placeholder="Ex: Teresina"
              class="flex-1"
              @input="debouncedSearch"
            />
            <UInput 
              v-model="searchStore.filters.state" 
              placeholder="UF"
              class="w-16"
              @input="debouncedSearch"
            />
          </div>
        </UFormField>

        <!-- Tipo de Imóvel -->
        <UFormField label="Tipo de Imóvel">
          <USelectMenu
            v-model="searchStore.filters.type"
            :items="propertyTypeOptions"
            value-attribute="value"
            placeholder="Selecione o tipo"
            class="w-full"
          />
        </UFormField>

        <!-- Preço -->
        <UFormField label="Faixa de Preço">
          <div class="flex items-center gap-2">
            <UInput 
              v-model="searchStore.filters.minPrice" 
              type="number" 
              placeholder="Mín"
              class="w-full"
              @input="debouncedSearch"
            >
              <template #leading>
                <span class="text-neutral-500 text-xs">R$</span>
              </template>
            </UInput>
            <span class="text-neutral-500">-</span>
            <UInput 
              v-model="searchStore.filters.maxPrice" 
              type="number" 
              placeholder="Máx"
              class="w-full"
              @input="debouncedSearch"
            >
              <template #leading>
                <span class="text-neutral-500 text-xs">R$</span>
              </template>
            </UInput>
          </div>
        </UFormField>
      </div>

      <!-- Advanced Filters Toggle -->
      <div class="flex justify-center">
        <UButton
          variant="ghost"
          color="neutral"
          :icon="showAdvanced ? 'i-heroicons-chevron-up' : 'i-heroicons-chevron-down'"
          trailing
          @click="showAdvanced = !showAdvanced"
        >
          {{ showAdvanced ? 'Ocultar Filtros Avançados' : 'Mostrar Filtros Avançados' }}
        </UButton>
      </div>

      <!-- Advanced Filters Area -->
      <div v-show="showAdvanced" class="transition-all duration-300">
        <UDivider class="mb-6" />
        <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
          
          <!-- Características -->
          <UFormField :label="t('search.bedrooms')">
            <UInput 
              v-model="searchStore.filters.minBedrooms" 
              type="number" 
              placeholder="0+"
              class="w-full"
              @input="debouncedSearch"
            />
          </UFormField>

          <UFormField label="Suítes">
            <UInput 
              v-model="searchStore.filters.minSuites" 
              type="number" 
              placeholder="0+"
              class="w-full"
              @input="debouncedSearch"
            />
          </UFormField>

          <UFormField :label="t('search.parking')">
            <UInput 
              v-model="searchStore.filters.minParkingSpaces" 
              type="number" 
              placeholder="0+"
              class="w-full"
              @input="debouncedSearch"
            />
          </UFormField>

          <!-- Comodidades -->
          <UFormField :label="t('search.filters')" class="lg:col-span-4">
            <USelectMenu
              v-model="searchStore.filters.amenities"
              :items="amenityOptions"
              value-attribute="value"
              multiple
              placeholder="Selecione as comodidades"
              class="w-full"
            >
              <template #label>
                <div v-if="searchStore.filters.amenities?.length" class="flex flex-wrap gap-1">
                  <UBadge 
                    v-for="amenity in searchStore.filters.amenities" 
                    :key="amenity"
                    size="xs"
                    variant="subtle"
                  >
                    {{ t(`amenity.${amenity}`) }}
                  </UBadge>
                </div>
                <span v-else>Selecione as comodidades</span>
              </template>
            </USelectMenu>
          </UFormField>
        </div>
      </div>
    </div>

    <template #footer>
      <div class="flex justify-between items-center">
        <span class="text-sm text-neutral-500">
          Resultados atualizados automaticamente
        </span>
        <div class="flex gap-3">
          <UButton 
            variant="ghost" 
            color="neutral" 
            icon="i-heroicons-arrow-path"
            @click="handleReset"
          >
            Limpar Filtros
          </UButton>
        </div>
      </div>
    </template>
  </UCard>
</template>
