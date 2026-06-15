<script setup lang="ts">
import { useSearchStore } from '~/stores/search'
import PropertyCatalog from '~/components/layout/PropertyCatalog.vue'
import SearchFilters from '~/components/property/SearchFilters.vue'

const { t } = useI18n()
const searchStore = useSearchStore()

// Initial search
onMounted(() => {
  searchStore.search()
})

async function handleSearch() {
  await searchStore.search(0)
}

async function handlePageChange(page: number) {
  await searchStore.search(page - 1)
}

function handleReset() {
  searchStore.resetFilters()
  handleSearch()
}

useSeoMeta({
  title: 'Busca de Imóveis - Marble & Arch',
  description: 'Encontre o imóvel dos seus sonhos na Marble & Arch. Utilize nossos filtros avançados para localizar casas, apartamentos e coberturas exclusivas.'
})
</script>

<template>
  <div class="space-y-12 pb-24">
    <!-- Header luxuoso -->
    <div class="border-b border-border bg-background pt-12 pb-8">
      <div class="w-full">
        <h1 class="text-h1 text-foreground mb-4">{{ t('search.title') }}</h1>
        <p class="text-body-lg text-text-dimmed max-w-3xl">Descubra as propriedades mais exclusivas através dos nossos filtros avançados. Encontre exatamente o que você procura.</p>
      </div>
    </div>

    <div class="w-full">
      <!-- Filtros com Glassmorphism -->
      <SearchFilters class="mb-12" @search="handleSearch" />

      <!-- Catálogo de Resultados -->
      <PropertyCatalog
        :properties="searchStore.results"
        :is-loading="searchStore.isLoading"
        :total-elements="searchStore.totalElements"
        :total-pages="searchStore.totalPages"
        :current-page="searchStore.currentPage"
        :empty-title="t('search.noResults')"
        empty-icon="i-heroicons-magnifying-glass"
        @page-change="handlePageChange"
        @reset-filters="handleReset"
      />
    </div>
  </div>
</template>
