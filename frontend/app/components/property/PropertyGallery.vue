<script setup lang="ts">
import type { PropertyImageDTO } from '~/types/property';

const props = withDefaults(defineProps<{
  images?: PropertyImageDTO[];
  isLoading?: boolean;
}>(), {
  images: () => [],
  isLoading: false
});

const activeImage = ref(0);
</script>

<template>
  <div v-if="isLoading">
    <UCard class="overflow-hidden p-0 border-border" :ui="{ body: 'p-0' }">
      <div class="grid grid-cols-1 lg:grid-cols-4 h-[500px]">
        <USkeleton class="lg:col-span-3 h-full w-full" />
        <div class="hidden lg:flex flex-col gap-2 p-2 bg-muted border-l border-border">
          <USkeleton v-for="i in 4" :key="i" class="aspect-video w-full rounded" />
        </div>
      </div>
    </UCard>
  </div>

  <UCard v-else class="overflow-hidden p-0 border-border" :ui="{ body: 'p-0' }">
    <div class="grid grid-cols-1 lg:grid-cols-4 h-[500px]">
      <!-- Main Image -->
      <div class="lg:col-span-3 relative bg-black flex items-center justify-center overflow-hidden">
        <NuxtImg
          v-if="images.length > 0"
          :src="images[activeImage].url"
          width="800"
          height="500"
          format="webp"
          class="max-w-full max-h-full object-contain"
        />
        <div v-else class="text-white text-6xl opacity-20">🏠</div>
        
        <!-- Mobile/Tablet Navigation Dots -->
        <div v-if="images.length > 1" class="absolute inset-x-0 bottom-4 flex justify-center gap-2">
          <UButton
            v-for="(_, index) in images"
            :key="index"
            variant="solid"
            :color="activeImage === index ? 'primary' : 'neutral'"
            class="w-3 h-3 p-0 rounded-full"
            @click="activeImage = index"
          />
        </div>
      </div>

      <!-- Thumbnails Sidebar (Desktop) -->
      <div v-if="images.length > 0" class="hidden lg:flex flex-col gap-2 p-2 overflow-y-auto bg-muted border-l border-border">
        <div
          v-for="(img, index) in images"
          :key="img.id"
          class="aspect-video relative cursor-pointer rounded overflow-hidden border border-transparent"
          :class="activeImage === index ? 'ring-2 ring-primary' : 'opacity-60 hover:opacity-100'"
          @click="activeImage = index"
        >
          <NuxtImg 
            :src="img.url" 
            width="150"
            height="85"
            format="webp"
            class="w-full h-full object-cover" 
          />
        </div>
      </div>
    </div>
  </UCard>
</template>
