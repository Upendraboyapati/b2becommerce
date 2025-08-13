import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5174,
    strictPort: true, // This will fail if port 5175 is not available
    host: true, // This enables listening on all network interfaces
  },
})
