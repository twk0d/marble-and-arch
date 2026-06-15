import { defineStore } from 'pinia'
import type { User } from '~/types'

function decodeJWT(token: string) {
    try {
        const base64Url = token.split('.')[1]
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
        // Handles browser and Node (Nuxt SSR) environment natively
        const jsonPayload = typeof Buffer !== 'undefined' 
            ? Buffer.from(base64, 'base64').toString('utf-8')
            : decodeURIComponent(atob(base64).split('').map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)).join(''))
        return JSON.parse(jsonPayload)
    } catch (e) {
        return null
    }
}

export const useAuthStore = defineStore('auth', () => {
    const token = useCookie<string | null>('auth_token', {
        maxAge: 60 * 60 * 24 * 7, // 1 week
        watch: true
    })
    
    const user = ref<User | null>(null)

    // Inicializa o usuário a partir do token salvo (caso haja recarregamento da página)
    if (token.value) {
        const payload = decodeJWT(token.value)
        if (payload) {
            user.value = {
                email: payload.sub,
                name: payload.name,
                role: payload.role
            }
        }
    }

    const isAuthenticated = computed(() => !!token.value)

    async function login(credentials: { email: string; password: string }) {
        try {
            const data = await $fetch<string>('/api/v1/auth/login', {
                method: 'POST',
                body: credentials,
                headers: {
                    'Content-Type': 'application/json'
                }
            })

            token.value = data
            const payload = decodeJWT(data)
            
            if (payload) {
                user.value = { 
                    email: payload.sub,
                    name: payload.name,
                    role: payload.role
                }
            } else {
                user.value = { email: credentials.email }
            }
            
            return data
        } catch (error) {
            console.error('Login failed:', error)
            throw error
        }
    }

    function logout() {
        token.value = null
        user.value = null
        navigateTo('/login')
    }

    return {
        token,
        user,
        isAuthenticated,
        login,
        logout
    }
})
