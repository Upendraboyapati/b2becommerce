import React from 'react';

const AboutPage = () => {
  return (
    <div className="max-w-3xl mx-auto py-10 px-4">
      <h1 className="text-3xl font-bold text-blue-600 mb-4">About Our B2B Platform</h1>
      <p className="text-lg text-gray-700 dark:text-gray-300 mb-4">
        Our B2B e-commerce platform is designed to streamline the buying and selling process
        between businesses. Whether you're a manufacturer, wholesaler, or retailer, our platform
        empowers you to manage products, orders, and payments efficiently.
      </p>
      <p className="text-gray-600 dark:text-gray-400">
        Key features include:
      </p>
      <ul className="list-disc pl-6 text-gray-700 dark:text-gray-300">
        <li>Role-based access for Buyers and Sellers</li>
        <li>Seller dashboard to manage products and orders</li>
        <li>Buyer dashboard to place and track orders</li>
        <li>Real-time notifications and payment tracking</li>
        <li>Secure authentication via Google OAuth or local login</li>
      </ul>
    </div>
  );
};

export default AboutPage;
