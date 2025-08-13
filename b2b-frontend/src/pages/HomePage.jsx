import React from 'react';
import { Link } from 'react-router-dom';

const HomePage = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-white via-blue-50 to-blue-100 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
        <div className="text-center">
          <h1 className="text-4xl font-extrabold text-gray-900 dark:text-white sm:text-5xl md:text-6xl leading-tight">
            <span className="block">Welcome to</span>
            <span className="block text-blue-600 dark:text-blue-400">B2B E-Commerce Platform</span>
          </h1>
          <p className="mt-6 max-w-2xl mx-auto text-lg text-gray-600 dark:text-gray-300 sm:text-xl">
            Streamline your business operations with our comprehensive B2B platform. Connect with suppliers, manage inventory, and grow your business.
          </p>

          <div className="mt-10 flex flex-col sm:flex-row sm:justify-center gap-4">
            <Link
              to="/register"
              className="px-6 py-3 text-white bg-blue-600 hover:bg-blue-700 rounded-lg text-lg font-medium transition duration-300 ease-in-out shadow-md"
            >
              Get Started
            </Link>
            <Link
              to="/about"
              className="px-6 py-3 bg-white text-blue-600 hover:bg-gray-100 dark:bg-gray-800 dark:text-gray-200 dark:hover:bg-gray-700 rounded-lg text-lg font-medium transition duration-300 ease-in-out shadow-md"
            >
              Learn More
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;