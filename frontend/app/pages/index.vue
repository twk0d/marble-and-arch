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

const { data: response, pending, error } = useFetch<any>('/api/v1/properties', {
  lazy: true,
  server: false
})

const mockProperties = [
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
]

const featuredProperties = computed(() => {
  if (response.value && response.value.success && response.value.data?.content?.length > 0) {
    return response.value.data.content.map((p: any) => ({
      id: p.id,
      title: p.title || 'Imóvel sem título',
      location: `${p.address?.city || ''}, ${p.address?.state || ''}`,
      price: new Intl.NumberFormat('pt-BR', { style: 'currency', currency: p.price?.currency || 'BRL' }).format(p.price?.amount || 0),
      imageUrl: p.mainImageUrl || 'https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=800&q=80',
      type: p.type || 'HOUSE'
    }))
  }
  return mockProperties
})
</script>

<template>
  <div class="space-y-24 pb-16">
    <!-- Hero Section (Magazine Style) -->
    <section class="relative rounded-[2rem] overflow-hidden min-h-[600px] lg:min-h-[700px] flex items-center justify-center border border-border">
      <NuxtImg 
        src="https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=1600&q=80" 
        alt="Luxury Hero" 
        class="absolute inset-0 w-full h-full object-cover"
        preload
      />
      <div class="absolute inset-0 bg-black/30 pointer-events-none" />
      
      <div class="relative z-10 w-full max-w-4xl px-4 flex flex-col items-center text-center">
        <h1 class="text-h1 text-white mb-6 drop-shadow-lg leading-tight">
          A Arte de Viver Bem, Elevada.
        </h1>
        <p class="text-body-lg text-white/90 mb-12 max-w-2xl drop-shadow-md">
          Explore as residências mais exclusivas, coberturas imponentes e arquiteturas premiadas no portfólio da Marble & Arch.
        </p>

        <!-- Glassmorphism Floating CTA -->
        <div class="bg-glass-sand w-full max-w-2xl rounded-2xl p-4 sm:p-6 shadow-ambient border border-white/20 flex flex-col sm:flex-row gap-4 items-center">
          <div class="flex-1 w-full flex items-center bg-white/50 dark:bg-black/20 rounded-lg px-4 py-3 border border-border">
             <UIcon name="i-heroicons-magnifying-glass" class="w-5 h-5 text-text-dimmed mr-3" />
             <input type="text" placeholder="Buscar por cidade, bairro ou ID..." class="bg-transparent border-none outline-none w-full text-foreground placeholder:text-text-dimmed text-body-base" />
          </div>
          <UButton to="/search" size="xl" color="primary" class="w-full sm:w-auto px-8 shadow-ambient">
            Explorar
          </UButton>
        </div>
      </div>
    </section>

    <!-- Categories Section -->
    <section class="max-w-6xl mx-auto">
      <div class="text-center mb-12">
        <h2 class="text-h2 text-foreground mb-4">Coleções Exclusivas</h2>
        <p class="text-body-lg text-text-dimmed">Encontre a propriedade que reflete sua personalidade.</p>
      </div>

      <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-6">
        <NuxtLink
          v-for="type in ['HOUSE', 'CONDOMINIUM_HOUSE', 'PENTHOUSE', 'STUDIO', 'APARTMENT', 'TOWNHOUSE']"
          :key="type"
          :to="`/search?type=${type}`"
          class="flex flex-col items-center text-center p-6 rounded-xl bg-card border border-border hover:border-primary transition-all duration-300 hover-card-lift group"
        >
          <div class="w-16 h-16 rounded-full bg-muted flex items-center justify-center mb-4 group-hover:bg-primary/10 transition-colors">
            <UIcon
              :name="categoryIcons[type]"
              class="w-8 h-8 text-primary/80 group-hover:text-primary transition-colors"
            />
          </div>
          <span class="text-label-sm tracking-widest text-foreground/80 group-hover:text-primary">{{ t(`propertyType.${type}`) }}</span>
        </NuxtLink>
      </div>
    </section>

    <!-- Featured Properties Section -->
    <section>
      <div class="flex justify-between items-end mb-12 border-b border-border pb-6">
        <div>
          <h2 class="text-h2 text-foreground mb-2">Imóveis de Destaque</h2>
          <p class="text-body-lg text-text-dimmed">Nossa curadoria com as melhores propriedades da semana.</p>
        </div>
        <UButton to="/search" variant="link" color="primary" icon="i-heroicons-arrow-right" trailing class="hidden sm:flex text-label-sm">
          Ver Catálogo Completo
        </UButton>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <UPageCard
          v-for="prop in featuredProperties"
          :key="prop.id"
          :to="`/search?type=${prop.type}`"
          class="overflow-hidden group hover-card-lift border-transparent bg-transparent hover:bg-card"
          :ui="{ body: 'p-0 sm:p-0', header: 'p-0 sm:p-0' }"
        >
          <template #header>
            <div class="aspect-[4/3] relative overflow-hidden rounded-xl bg-muted">
              <NuxtImg
                :src="prop.imageUrl"
                :alt="prop.title"
                width="600"
                height="450"
                format="webp"
                loading="lazy"
                class="w-full h-full object-cover transition-transform duration-700 ease-out group-hover:scale-105"
              />
              <UBadge class="absolute top-4 right-4 shadow-ambient px-3 py-1 bg-glass-sand text-foreground border border-white/20" variant="solid">
                {{ t(`propertyType.${prop.type}`) }}
              </UBadge>
            </div>
          </template>
          <template #title>
            <h3 class="text-h4 text-foreground mt-4 line-clamp-1 group-hover:text-primary transition-colors">{{ prop.title }}</h3>
          </template>
          <template #description>
            <div class="flex items-center gap-1.5 text-body-sm text-text-dimmed mt-1 mb-2">
              <UIcon name="i-heroicons-map-pin" class="w-4 h-4 text-primary/70" />
              {{ prop.location }}
            </div>
            <span class="text-primary font-bold text-h5">{{ prop.price }}</span>
          </template>
        </UPageCard>
      </div>
      
      <div class="mt-8 flex justify-center sm:hidden">
        <UButton to="/search" variant="outline" color="neutral" block>
          Ver Catálogo Completo
        </UButton>
      </div>
    </section>

    <!-- Advantages Section -->
    <section class="bg-muted rounded-[2rem] p-12 lg:p-24 border border-border">
      <div class="text-center mb-16">
        <h2 class="text-h2 text-foreground mb-4">Vantagens Marble & Arch</h2>
        <p class="text-body-lg text-text-dimmed max-w-2xl mx-auto">Sua propriedade tratada como uma obra de arte, com marketing cirúrgico e alcance global.</p>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12">
        <div class="flex flex-col items-center text-center gap-4">
          <UIcon name="i-heroicons-eye" class="w-12 h-12 text-primary/80" />
          <h4 class="text-h4 text-foreground">Visibilidade Premium</h4>
          <p class="text-body-sm text-text-dimmed">Audiência qualificada e interessada no mais alto padrão.</p>
        </div>
        <div class="flex flex-col items-center text-center gap-4">
          <UIcon name="i-heroicons-presentation-chart-line" class="w-12 h-12 text-primary/80" />
          <h4 class="text-h4 text-foreground">Marketing Digital</h4>
          <p class="text-body-sm text-text-dimmed">Estratégias de tráfego de nicho para acelerar a liquidez.</p>
        </div>
        <div class="flex flex-col items-center text-center gap-4">
          <UIcon name="i-heroicons-shield-check" class="w-12 h-12 text-primary/80" />
          <h4 class="text-h4 text-foreground">Sigilo e Segurança</h4>
          <p class="text-body-sm text-text-dimmed">Transações estruturadas para garantir total privacidade.</p>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section>
      <div class="relative overflow-hidden rounded-[2rem] bg-neutral-950 flex flex-col md:flex-row items-center border border-neutral-800 shadow-ambient">
        <div class="absolute inset-0 bg-gradient-to-br from-primary/20 via-transparent to-transparent opacity-30 pointer-events-none" />
        
        <div class="w-full md:w-1/2 p-12 lg:p-20 relative z-10 flex flex-col items-start text-left">
          <h2 class="text-h2 text-white mb-6">Pronto para o próximo passo?</h2>
          <p class="text-body-lg text-neutral-300 mb-10 max-w-md">
            Seja para comprar, vender ou anunciar. Nossa equipe está pronta para apresentar as melhores oportunidades do mercado imobiliário.
          </p>
          <div class="flex flex-wrap gap-4">
            <UButton size="xl" color="primary" variant="solid" to="/signup" class="shadow-lg">
              Começar Agora
            </UButton>
            <UButton size="xl" color="white" variant="ghost" to="#">
              Falar com Consultor
            </UButton>
          </div>
        </div>
        
        <div class="hidden md:block w-full md:w-1/2 h-full min-h-[400px] relative">
          <NuxtImg 
            src="https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&w=800&q=80" 
            alt="Interior" 
            class="absolute inset-0 w-full h-full object-cover"
          />
          <div class="absolute inset-0 bg-gradient-to-r from-neutral-950 via-neutral-950/40 to-transparent pointer-events-none" />
        </div>
      </div>
    </section>
  </div>
</template>
