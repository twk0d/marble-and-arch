<script setup lang="ts">
const { t } = useI18n()

const categoryIcons: Record<string, string> = {
  HOUSE: 'i-heroicons-home',
  CONDOMINIUM_HOUSE: 'i-heroicons-home-modern',
  PENTHOUSE: 'i-heroicons-building-office',
  STUDIO: 'i-heroicons-square-3-stack-3d',
  APARTMENT: 'i-heroicons-building-office-2',
  TOWNHOUSE: 'i-heroicons-building-storefront'
}

const featuredProperties = computed(() => [
  {
    id: 'featured-1',
    title: 'Mansão Contemporânea',
    location: 'Jardins, São Paulo - SP',
    price: 'R$ 8.900.000,00',
    imageUrl: 'https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&w=800&q=80',
    type: 'HOUSE'
  },
  {
    id: 'featured-2',
    title: 'Cobertura Duplex',
    location: 'Ipanema, Rio de Janeiro - RJ',
    price: 'R$ 12.500.000,00',
    imageUrl: 'https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&w=800&q=80',
    type: 'PENTHOUSE'
  },
  {
    id: 'featured-3',
    title: 'Casa em Condomínio Fechado',
    location: 'Alphaville, Barueri - SP',
    price: 'R$ 6.200.000,00',
    imageUrl: 'https://images.unsplash.com/photo-1613977257363-707ba9348227?auto=format&fit=crop&w=800&q=80',
    type: 'CONDOMINIUM_HOUSE'
  }
])
</script>

<template>
  <div>
    <UMain>
      <!-- Hero Section -->
      <UPageHero
        :title="t('header.title')"
        :description="t('styleguide.hero.description')"
        align="center"
        class="relative overflow-hidden bg-grid-pattern py-24 bg-glow-radial border-b border-border"
      >
        <template #links>
          <div class="flex justify-center gap-4">
            <UButton to="/search" size="xl" icon="i-heroicons-magnifying-glass" class="shadow-md hover:scale-105 transition-transform">
              Encontrar Imóveis
            </UButton>
            <UButton to="/admin/properties/create" size="xl" variant="outline" color="neutral" class="hover:bg-muted transition-colors">
              Anunciar Imóvel
            </UButton>
          </div>
        </template>
      </UPageHero>

      <!-- Categories Section -->
      <UPageSection
        title="Explore por Categorias"
        description="Encontre o imóvel ideal para o seu estilo de vida"
        class="py-16"
      >
        <UPageGrid>
          <UPageCard
            v-for="type in ['HOUSE', 'CONDOMINIUM_HOUSE', 'PENTHOUSE', 'STUDIO', 'APARTMENT', 'TOWNHOUSE']"
            :key="type"
            :title="t(`propertyType.${type}`)"
            :to="`/search?type=${type}`"
            class="text-center group hover-card-lift"
          >
            <template #icon>
              <UIcon
                :name="categoryIcons[type]"
                class="w-10 h-10 mx-auto text-primary group-hover:scale-110 transition-transform duration-300"
              />
            </template>
          </UPageCard>
        </UPageGrid>
      </UPageSection>

      <!-- Featured Luxury Properties Section -->
      <UPageSection
        title="Imóveis de Luxo em Destaque"
        description="Uma seleção exclusiva das melhores propriedades em nosso catálogo"
        variant="subtle"
        class="py-16"
      >
        <UPageGrid>
          <UPageCard
            v-for="prop in featuredProperties"
            :key="prop.id"
            :title="prop.title"
            :description="prop.location"
            class="overflow-hidden group hover-card-lift"
            :ui="{ body: 'p-4' }"
          >
            <template #header>
              <div class="aspect-video relative overflow-hidden bg-muted flex items-center justify-center">
                <NuxtImg
                  :src="prop.imageUrl"
                  :alt="prop.title"
                  width="400"
                  height="225"
                  format="webp"
                  loading="lazy"
                  class="w-full h-full object-cover transition-transform duration-700 ease-out group-hover:scale-105"
                />
                <div class="absolute inset-0 bg-gradient-to-t from-black/20 via-transparent to-transparent opacity-80 pointer-events-none" />
                <UBadge class="absolute top-3 right-3 shadow-sm" variant="solid" color="primary">
                  {{ t(`propertyType.${prop.type}`) }}
                </UBadge>
              </div>
            </template>
            <template #footer>
              <div class="flex justify-between items-center mt-1">
                <span class="text-primary font-bold text-body-lg">{{ prop.price }}</span>
                <UButton variant="ghost" icon="i-heroicons-arrow-right" color="primary" :to="`/search?type=${prop.type}`" />
              </div>
            </template>
          </UPageCard>
        </UPageGrid>
        
        <template #bottom>
          <div class="flex justify-center mt-12">
            <UButton to="/search" variant="link" size="lg" icon="i-heroicons-plus">
              Ver Catálogo Completo
            </UButton>
          </div>
        </template>
      </UPageSection>

      <!-- Advantages Section -->
      <UPageSection
        title="Vantagens de Anunciar na Marble & Arch"
        description="Por que somos a escolha número um para vender seu imóvel de alto padrão"
        class="py-24"
      >
        <UPageGrid :columns="3">
          <UPageFeature
            title="Visibilidade Premium"
            description="Seu imóvel exposto para uma audiência qualificada e interessada em alto padrão."
            icon="i-heroicons-eye"
          />
          <UPageFeature
            title="Marketing Digital"
            description="Estratégias avançadas de tráfego pago e redes sociais para acelerar a venda."
            icon="i-heroicons-presentation-chart-line"
          />
          <UPageFeature
            title="Suporte Especializado"
            description="Consultores prontos para auxiliar em todas as etapas burocráticas e jurídicas."
            icon="i-heroicons-user-group"
          />
          <UPageFeature
            title="Fotografia Profissional"
            description="Destaque os melhores ângulos da sua propriedade com produção de alta qualidade."
            icon="i-heroicons-camera"
          />
          <UPageFeature
            title="Relatórios de Performance"
            description="Acompanhe em tempo real o interesse e as visitas agendadas para o seu anúncio."
            icon="i-heroicons-document-chart-bar"
          />
          <UPageFeature
            title="Segurança e Sigilo"
            description="Garantimos a total privacidade e segurança em todas as transações realizadas."
            icon="i-heroicons-shield-check"
          />
        </UPageGrid>
      </UPageSection>

      <!-- CTA Section -->
      <UPageSection class="py-16">
        <UPageCard
          title="Pronto para o próximo passo?"
          description="Seja para comprar ou vender, a Marble & Arch é sua parceira ideal."
          class="bg-neutral-900 dark:bg-neutral-950 text-white text-center bg-grid-pattern relative overflow-hidden py-12 px-6 rounded-3xl border border-neutral-800"
          :ui="{ 
            body: 'relative z-10',
            title: 'text-white text-h2 mb-4', 
            description: 'text-neutral-300 text-body-lg max-w-xl mx-auto mb-6' 
          }"
        >
          <div class="absolute inset-0 bg-gradient-to-r from-primary/10 via-secondary/5 to-primary/10 opacity-30 pointer-events-none" />
          <template #footer>
            <div class="flex justify-center gap-4 relative z-10">
              <UButton size="xl" color="neutral" variant="solid" to="/signup" class="shadow-lg hover:scale-105 transition-transform">
                Começar Agora
              </UButton>
              <UButton size="xl" color="white" variant="outline" to="/contact" class="hover:bg-white/10 transition-colors">
                Falar com Consultor
              </UButton>
            </div>
          </template>
        </UPageCard>
      </UPageSection>
    </UMain>
  </div>
</template>
