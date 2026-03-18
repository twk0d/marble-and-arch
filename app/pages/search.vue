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
  title: 'Busca de Imóveis - Keystone',
  description: 'Encontre o imóvel dos seus sonhos na Keystone. Utilize nossos filtros avançados para localizar casas, apartamentos e coberturas exclusivas.'
})
</script>

<template>
  <div>
    <UContainer class="py-8">
      <UPageHeader
        :title="t('search.title')"
        :description="t('styleguide.hero.description')"
        class="mb-8"
      />

      <SearchFilters class="mb-12" @search="handleSearch" />

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
    </UContainer>
  </div>
</template>
