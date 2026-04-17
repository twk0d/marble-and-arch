import { defineStore } from 'pinia'
import type { PageableFilters, PropertySummaryDTO } from '~/types'
import type { Page } from '~/types/page'

export const useSearchStore = defineStore('search', () => {
    const filters = ref<PageableFilters>({
        type: undefined,
        minPrice: undefined,
        maxPrice: undefined,
        minBedrooms: undefined,
        minSuites: undefined,
        minParkingSpaces: undefined,
        city: '',
        state: '',
        amenities: []
    })

    const results = ref<PropertySummaryDTO[]>([])
    const totalElements = ref(0)
    const totalPages = ref(0)
    const currentPage = ref(0)
    const isLoading = ref(false)
    
    // Simple Cache
    const cache = new Map<string, Page<PropertySummaryDTO>>()

    async function search(page = 0, size = 12) {
        isLoading.value = true
        try {
            // Remove undefined/empty filters before sending
            const activeFilters = Object.fromEntries(
                Object.entries(filters.value).filter(([_, v]) => v !== undefined && v !== '' && (!Array.isArray(v) || v.length > 0))
            )
            
            // Create cache key
            const cacheKey = JSON.stringify({ page, size, ...activeFilters })
            
            if (cache.has(cacheKey)) {
                const data = cache.get(cacheKey)!
                results.value = data.content
                totalElements.value = data.totalElements
                totalPages.value = data.totalPages
                currentPage.value = data.number
                isLoading.value = false
                return
            }

            const data = await $fetch<Page<PropertySummaryDTO>>('/api/v1/property-management/search', {
                method: 'GET',
                query: { page, size, ...activeFilters }
            })
            
            // Store in cache
            cache.set(cacheKey, data)

            results.value = data.content
            totalElements.value = data.totalElements
            totalPages.value = data.totalPages
            currentPage.value = data.number
        } catch (error) {
            console.error('Search failed:', error)
            throw error
        } finally {
            isLoading.value = false
        }
    }

    function resetFilters() {
        cache.clear() // Clear cache on reset
        filters.value = {
            type: undefined,
            minPrice: undefined,
            maxPrice: undefined,
            minBedrooms: undefined,
            minSuites: undefined,
            minParkingSpaces: undefined,
            city: '',
            state: '',
            amenities: []
        }
    }

    return {
        filters,
        results,
        totalElements,
        totalPages,
        currentPage,
        isLoading,
        search,
        resetFilters
    }
})
