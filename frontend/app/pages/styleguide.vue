<script setup lang="ts">
import type { NavigationMenuItem } from '@nuxt/ui'
import { PropertyType, type PropertySummaryDTO } from '~/types';
import ColorSwatch from '~/components/styleguide/ColorSwatch.vue';
import PropertyCatalog from "~/components/layout/PropertyCatalog.vue";
import SearchFilters from "~/components/property/SearchFilters.vue";
import PropertyForm from "~/components/property/PropertyForm.vue";
import ImageUpload from "~/components/property/ImageUpload.vue";
import PropertyQuickView from "~/components/property/PropertyQuickView.vue";

const {t} = useI18n();
const toast = useToast();
const colorMode = useColorMode();

definePageMeta({
  layout: false
})

const placeholderItems = computed<NavigationMenuItem[]>(() => [
  {
    label: 'Placeholder',
    children: [
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
    ]
  },
  {
    label: 'Placeholder',
    children: [
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
    ]
  },
  {
    label: 'Placeholder',
    children: [
      {label: 'Placeholder'},
      {label: 'Placeholder'},
    ]
  },
  {
    label: 'Placeholder',
    children: [
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
      {label: 'Placeholder'},
    ]
  }
]);

// 1. Mock Data for Catalog
const sampleProperties: PropertySummaryDTO[] = [
  {
    id: "1",
    type: PropertyType.HOUSE,
    city: "Teresina",
    state: "PI",
    priceAmount: 450000,
    priceCurrency: "R$",
    mainImageUrl: "https://picsum.photos/id/1015/800/600",
    active: true
  },
  {
    id: "2",
    type: PropertyType.STUDIO,
    city: "Teresina",
    state: "PI",
    priceAmount: 280000,
    priceCurrency: "R$",
    mainImageUrl: "https://picsum.photos/id/1031/800/600",
    active: true
  },
  {
    id: "3",
    type: PropertyType.PENTHOUSE,
    city: "Parnaíba",
    state: "PI",
    priceAmount: 650000,
    priceCurrency: "R$",
    mainImageUrl: "https://picsum.photos/id/1043/800/600",
    active: true
  }
];

// 2. Mock State for Quick View
const isQuickViewOpen = ref(false);
const selectedMockProperty = ref<PropertySummaryDTO | null>(sampleProperties[0]);

function openQuickView(property?: PropertySummaryDTO) {
    if (property) selectedMockProperty.value = property;
    else selectedMockProperty.value = sampleProperties[0];
    isQuickViewOpen.value = true;
}

// 3. Mock State for Media Management
const mockPropertyId = "styleguide-mock-id";
const mockImages = ref([
  { id: '1', url: 'https://picsum.photos/400/300?random=1', active: true },
  { id: '2', url: 'https://picsum.photos/400/300?random=2', active: false },
  { id: '3', url: 'https://picsum.photos/400/300?random=3', active: true },
  { id: '4', url: 'https://picsum.photos/400/300?random=4', active: true },
]);

function handleImageReorder({ id, direction }: { id: string, direction: 'up' | 'down' }) {
  const index = mockImages.value.findIndex(img => img.id === id);
  if (index === -1) return;
  const newIndex = direction === 'up' ? index - 1 : index + 1;
  if (newIndex >= 0 && newIndex < mockImages.value.length) {
    const temp = mockImages.value[index];
    mockImages.value[index] = mockImages.value[newIndex];
    mockImages.value[newIndex] = temp;
  }
}

function handleImageStatusChange({ id, active }: { id: string, active: boolean }) {
    const image = mockImages.value.find(img => img.id === id);
    if (image) image.active = active;
}

// Helpers
function showToast(type: 'success' | 'error' | 'warning' | 'info') {
  const configs = {
    success: { title: t('styleguide.notifications.trigger.success'), description: t('notifications.description'), color: 'success' as const, icon: 'i-heroicons-check-circle' },
    error: { title: t('styleguide.notifications.trigger.error'), description: t('notifications.description'), color: 'error' as const, icon: 'i-heroicons-x-circle' },
    warning: { title: t('styleguide.notifications.trigger.warning'), description: t('notifications.description'), color: 'warning' as const, icon: 'i-heroicons-exclamation-triangle' },
    info: { title: t('styleguide.notifications.trigger.info'), description: t('notifications.description'), color: 'primary' as const, icon: 'i-heroicons-information-circle' }
  };
  toast.add(configs[type]);
}

function handleSearch() {
  console.log('Search triggered in styleguide');
}

function handleSubmit(data: any) {
  console.log('Form submitted in styleguide:', data);
}

const allColors = computed(() => {
  const isDark = colorMode.value === 'dark';
  return [
    { colorClass: 'bg-primary', name: t('colorSwatch.main.primary.name'), hex: isDark ? '#FFFFFF' : '#19191a', description: t('colorSwatch.main.primary.description') },
    { colorClass: 'bg-secondary', name: t('colorSwatch.main.secondary.name'), hex: isDark ? '#F5F5DC' : '#FDFCF7', description: t('colorSwatch.main.secondary.description') },
    { colorClass: 'bg-background', name: t('colorSwatch.system.background.name'), hex: isDark ? '#1A1625' : '#FAF7F2', description: t('colorSwatch.system.background.description'), hasBorder: true },
    { colorClass: 'bg-card', name: t('colorSwatch.system.surface.name'), hex: isDark ? '#2D2438' : '#FFFFFF', description: t('colorSwatch.system.surface.description'), hasBorder: true },
    { colorClass: 'bg-muted', name: t('colorSwatch.system.highlight.name'), hex: isDark ? '#2C283A' : '#F3F4F6', description: t('colorSwatch.system.highlight.description'), hasBorder: true },
    { colorClass: 'bg-foreground', name: t('colorSwatch.system.text.name'), hex: isDark ? '#F9FAFB' : '#1F2937', description: t('colorSwatch.system.text.description'), hasBorder: false, isText: true }
  ];
});
</script>

<template>
  <div>
    <LayoutHeader :navigation-items="placeholderItems"/>
    <UMain>
      <UPageHero
        :title="t('styleguide.hero.title')"
        :description="t('styleguide.hero.description')"
        align="center"
      >
        <template #headline>
          <span class="text-body-muted uppercase tracking-widest text-xs">
            {{ t('styleguide.hero.subtitle') }}
          </span>
        </template>
        <template #body>
          <div class="flex flex-wrap justify-center gap-4">
            <UButton to="#filters" variant="outline" color="neutral">{{ t('styleguide.sections.filters') }}</UButton>
            <UButton to="#propertyCatalog" variant="outline" color="neutral">{{ t('styleguide.sections.catalog') }}</UButton>
            <UButton to="#quickView" variant="outline" color="neutral">Card Expandido (Quick View)</UButton>
            <UButton to="#forms" variant="outline" color="neutral">{{ t('styleguide.sections.forms') }}</UButton>
            <UButton to="#upload" variant="outline" color="neutral">{{ t('styleguide.sections.upload') }}</UButton>
            <UButton to="#typography" variant="outline" color="neutral">{{ t('styleguide.sections.typography') }}</UButton>
            <UButton to="#colors" variant="outline" color="neutral">{{ t('styleguide.sections.colors') }}</UButton>
            <UButton to="#borders" variant="outline" color="neutral">{{ t('styleguide.sections.borders') }}</UButton>
            <UButton to="#shadows" variant="outline" color="neutral">{{ t('styleguide.sections.shadows') }}</UButton>
            <UButton to="#notifications" variant="outline" color="neutral">{{ t('styleguide.sections.notifications') }}</UButton>
          </div>
        </template>
      </UPageHero>

      <!-- Search Filters Section -->
      <UPageSection id="filters" :title="t('styleguide.sections.filters')" :description="t('styleguide.filters.description')">
        <SearchFilters @search="handleSearch" />
      </UPageSection>

      <!-- Catalog Section -->
      <UPageSection id="propertyCatalog" :title="t('styleguide.sections.catalog')" :description="t('styleguide.catalog.description')">
        <PropertyCatalog 
            :properties="sampleProperties" 
            :total-elements="3" 
            :total-pages="1" 
            @select="openQuickView"
        />
      </UPageSection>

      <!-- Quick View Section -->
      <UPageSection id="quickView" title="Card Expandido (Quick View)" description="Visualização rápida de detalhes do imóvel em modal, acionada ao clicar em um item do catálogo.">
        <div class="flex justify-center">
          <UButton icon="i-heroicons-arrows-pointing-out" color="primary" size="xl" @click="openQuickView()">
            Abrir Demonstração (Quick View)
          </UButton>
        </div>
      </UPageSection>

      <!-- Forms Section -->
      <UPageSection id="forms" :title="t('styleguide.sections.forms')" :description="t('styleguide.forms.description')">
        <UCard class="border-border p-8">
          <PropertyForm @submit="handleSubmit" />
        </UCard>
      </UPageSection>

      <!-- Upload Section -->
      <UPageSection id="upload" :title="t('styleguide.sections.upload')" :description="t('styleguide.upload.description')">
        <UCard class="border-border p-8">
          <ImageUpload 
            :property-id="mockPropertyId" 
            :existing-images="mockImages"
            @image-reorder="handleImageReorder"
            @image-status-change="handleImageStatusChange"
          />
        </UCard>
      </UPageSection>

      <!-- Typography Section -->
      <UPageSection id="typography" :title="t('styleguide.sections.typography')">
        <UPageGrid class="lg:grid-cols-2">
          <UCard class="bg-card border-border">
            <template #header>
              <h4 class="text-h4">{{ t('styleguide.typography.titles.title') }}</h4>
            </template>
            <div class="space-y-6">
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-h1</span>
                <h1 class="text-h1">{{ t('styleguide.typography.titles.h1') }}</h1>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-h2</span>
                <h2 class="text-h2">{{ t('styleguide.typography.titles.h2') }}</h2>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-h3</span>
                <h3 class="text-h3">{{ t('styleguide.typography.titles.h3') }}</h3>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-h4</span>
                <h4 class="text-h4">{{ t('styleguide.typography.titles.h4') }}</h4>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-h5</span>
                <h5 class="text-h5">{{ t('styleguide.typography.titles.h5') }}</h5>
              </div>
            </div>
          </UCard>

          <UCard class="bg-card border-border">
            <template #header>
              <h4 class="text-h4">{{ t('styleguide.typography.body.title') }}</h4>
            </template>
            <div class="space-y-6">
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-body-lg</span>
                <p class="text-body-lg">{{ t('styleguide.typography.body.lg') }}</p>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-body-base</span>
                <p class="text-body-base">{{ t('styleguide.typography.body.base') }}</p>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-body-sm</span>
                <p class="text-body-sm">{{ t('styleguide.typography.body.sm') }}</p>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-body-xs</span>
                <p class="text-body-xs">{{ t('styleguide.typography.body.xs') }}</p>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-body-bold</span>
                <p class="text-body-bold">{{ t('styleguide.typography.body.bold') }}</p>
              </div>
              <div>
                <span class="text-body-xs text-neutral-400 mb-1 block">.text-body-muted</span>
                <p class="text-body-muted">{{ t('styleguide.typography.body.muted') }}</p>
              </div>
            </div>
          </UCard>
        </UPageGrid>
      </UPageSection>

      <!-- Colors Section -->
      <UPageSection id="colors" :title="t('styleguide.sections.colors')">
        <UCard class="bg-card border-border">
          <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-8">
            <div v-for="color in allColors" :key="color.name">
              <ColorSwatch
                :color-class="color.colorClass"
                :name="color.name"
                :hex="color.hex"
                :description="color.description"
                :has-border="color.hasBorder"
                :is-text="color.isText"
              />
            </div>
          </div>
        </UCard>
      </UPageSection>

      <!-- Borders Section -->
      <UPageSection id="borders" :title="t('styleguide.borders.title')" :description="t('styleguide.borders.description')">
        <UCard class="bg-card border-border">
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 p-4">
            <div class="space-y-2">
              <div class="flex items-center gap-2">
                <span class="text-body-bold">{{ t('styleguide.borders.solid.title') }}</span>
                <span class="text-body-xs text-neutral-400 font-mono">.border-border</span>
              </div>
              <div class="h-20 w-full bg-background border border-border rounded-lg flex items-center justify-center text-body-xs text-neutral-400">
                {{ t('styleguide.borders.solid.description') }}
              </div>
            </div>

            <div class="space-y-2">
              <div class="flex items-center gap-2">
                <span class="text-body-bold">{{ t('styleguide.borders.dashed.title') }}</span>
                <span class="text-body-xs text-neutral-400 font-mono">.border-dashed</span>
              </div>
              <div class="h-20 w-full bg-background border-2 border-dashed border-border rounded-lg flex items-center justify-center text-body-xs text-neutral-400">
                {{ t('styleguide.borders.dashed.description') }}
              </div>
            </div>

            <div class="space-y-2">
              <div class="flex items-center gap-2">
                <span class="text-body-bold">{{ t('styleguide.borders.divider.title') }}</span>
                <span class="text-body-xs text-neutral-400 font-mono">UDivider / .border-t</span>
              </div>
              <div class="h-20 w-full bg-background rounded-lg flex flex-col justify-center p-4">
                <div class="w-full border-t border-border" />
                <span class="mt-2 text-body-xs text-neutral-400">{{ t('styleguide.borders.divider.description') }}</span>
              </div>
            </div>
          </div>
        </UCard>
      </UPageSection>

      <!-- Shadows Section -->
      <UPageSection id="shadows" :title="t('styleguide.shadows.title')" :description="t('styleguide.shadows.description')">
        <UCard class="bg-card border-border">
          <div class="grid grid-cols-1 md:grid-cols-3 gap-12 p-8">
            <div class="space-y-4 text-center">
              <div class="h-24 w-full bg-card border border-border rounded-xl shadow-sm flex items-center justify-center">
                <span class="text-body-sm">{{ t('styleguide.shadows.sm.title') }}</span>
              </div>
              <p class="text-body-xs text-neutral-400">{{ t('styleguide.shadows.sm.description') }}</p>
            </div>

            <div class="space-y-4 text-center">
              <div class="h-24 w-full bg-card border border-border rounded-xl shadow-md flex items-center justify-center">
                <span class="text-body-sm">{{ t('styleguide.shadows.md.title') }}</span>
              </div>
              <p class="text-body-xs text-neutral-400">{{ t('styleguide.shadows.md.description') }}</p>
            </div>

            <div class="space-y-4 text-center">
              <div class="h-24 w-full bg-card border border-border rounded-xl shadow-xl flex items-center justify-center">
                <span class="text-body-sm">{{ t('styleguide.shadows.xl.title') }}</span>
              </div>
              <p class="text-body-xs text-neutral-400">{{ t('styleguide.shadows.xl.description') }}</p>
            </div>
          </div>
        </UCard>
      </UPageSection>

      <!-- Notifications Section -->
      <UPageSection id="notifications" :title="t('styleguide.sections.notifications')" :description="t('styleguide.notifications.description')">
        <UCard class="border-border">
          <div class="flex flex-wrap gap-4 p-4 justify-center">
            <UButton color="success" icon="i-heroicons-check-circle" @click="showToast('success')">{{ t('styleguide.notifications.trigger.success') }}</UButton>
            <UButton color="error" icon="i-heroicons-x-circle" @click="showToast('error')">{{ t('styleguide.notifications.trigger.error') }}</UButton>
            <UButton color="warning" icon="i-heroicons-exclamation-triangle" @click="showToast('warning')">{{ t('styleguide.notifications.trigger.warning') }}</UButton>
            <UButton color="primary" icon="i-heroicons-information-circle" @click="showToast('info')">{{ t('styleguide.notifications.trigger.info') }}</UButton>
          </div>
        </UCard>
      </UPageSection>

      <!-- Quick View Modal (Design Prototype) -->
      <PropertyQuickView 
        v-model="isQuickViewOpen" 
        :property="selectedMockProperty" 
        @view-full="isQuickViewOpen = false; showToast('info')" 
      />
    </UMain>
  </div>
</template>
