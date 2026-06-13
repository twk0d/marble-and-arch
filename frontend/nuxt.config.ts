// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    compatibilityDate: '2025-07-15',
    devtools: {
      enabled: true,

      timeline: {
        enabled: true
      }
    },

    modules: [
      '@nuxt/eslint',
      '@nuxt/image',
      '@nuxt/scripts',
      '@nuxt/test-utils',
      '@nuxt/ui',
      '@nuxtjs/i18n',
      '@nuxt/fonts',
      '@pinia/nuxt',
      '@nuxt/hints'
    ],

    image: {
        screens: {
            xs: 320,
            sm: 640,
            md: 768,
            lg: 1024,
            xl: 1280
        },
        format: ['webp', 'jpg'],
        quality: 80
    },

    nitro: {
        routeRules: {
            '/api/v1/**': {
                proxy: {
                    to: (process.env.NUXT_PUBLIC_API_BASE || 'http://localhost:8081/api/v1') + '/**',
                },
                headers: {
                    'Connection': 'keep-alive'
                }
            },
            '/admin/**': { ssr: false },
            '/styleguide/**': { ssr: false }
        }
    },

    fonts: {
        families: [
            {name: 'Playfair Display', provider: 'google', weights: ['400', '500', '600', '700', '800']},
            {name: 'Inter', provider: 'google', weights: ['400', '500', '600', '700']}
        ]
    },

    css: ['~/assets/css/main.css'],

    vite: {
        optimizeDeps: {
            include: [
                '@intlify/core',
                '@intlify/core-base',
                '@intlify/message-compiler',
                '@intlify/shared',
                '@intlify/utils/h3',
                '@vue/devtools-core',
                '@vue/devtools-kit',
                'ufo',
                'vue-i18n'
            ]
        }
    },

    i18n: {
        strategy: 'no_prefix',
        defaultLocale: 'pt-BR',
        langDir: '../i18n/locales',
        locales: [
            {
                code: 'en',
                iso: 'en-US',
                file: 'en.json',
                name: 'English'
            },
            {
                code: 'pt-BR',
                iso: 'pt-BR',
                file: 'pt-BR.json',
                name: 'Portuguese'
            }
        ],
        vueI18n: './i18n.config.ts'
    }
})