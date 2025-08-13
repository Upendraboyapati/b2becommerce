import { useEffect } from 'react';
import { useAuth } from '../hooks/useAuth';

const GoogleAuth = ({ mode = 'login' }) => {
  const { loginWithGoogle, registerWithGoogle } = useAuth();

  useEffect(() => {
    const loadGoogleScript = () => {
      const script = document.createElement('script');
      script.src = 'https://accounts.google.com/gsi/client';
      script.async = true;
      script.defer = true;
      document.head.appendChild(script);

      script.onload = () => {
        window.google.accounts.id.initialize({
          client_id: import.meta.env.VITE_GOOGLE_CLIENT_ID,
          callback: handleGoogleResponse
        });

        window.google.accounts.id.renderButton(
          document.getElementById('googleButton'),
          { theme: 'outline', size: 'large', width: '100%' }
        );
      };
    };

    loadGoogleScript();
  }, []);

  const handleGoogleResponse = async (response) => {
    if (response.credential) {
      await loginWithGoogle(response.credential);
    }
  };

  return (
    <div className="mt-4">
      <div className="relative">
        <div className="absolute inset-0 flex items-center">
          <div className="w-full border-t border-gray-300 dark:border-gray-600"></div>
        </div>
        <div className="relative flex justify-center text-sm">
          <span className="px-2 bg-white dark:bg-gray-800 text-gray-500 dark:text-gray-400">
            Or continue with
          </span>
        </div>
      </div>
      <div className="mt-4">
        <div id="googleButton"></div>
      </div>
    </div>
  );
};

export default GoogleAuth;
