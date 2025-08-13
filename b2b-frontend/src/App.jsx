import { BrowserRouter,Link } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import AppRoutes from './routes/AppRoutes';
import DarkModeToggle from './components/DarkModeToggle';

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="min-h-screen bg-gray-50 dark:bg-gray-900 text-gray-900 dark:text-white">
          {/* Top Navigation */}
          <header className="bg-white dark:bg-gray-800 shadow-sm">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <div className="flex justify-between items-center h-16">
                <div className="flex items-center">
                  <Link to="/" className="flex items-center">
                    <img src="/src/assets/images/projectlogo/b2b.png" alt="B2B Logo" className="h-10 w-auto mr-3" />
                  </Link>
                  <nav className="hidden md:flex space-x-8 ml-10">
                    <Link to="/" className="nav-link font-medium">Home</Link>
                    <Link to="/about" className="nav-link">About</Link>
                    <Link to="/register" className="nav-link">Register</Link>
                    <Link to="/login" className="btn btn-primary">Login</Link>
                  </nav>
                </div>
                <DarkModeToggle />
              </div>
            </div>
          </header>

          {/* Routes Rendered Here */}
          <AppRoutes />
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
