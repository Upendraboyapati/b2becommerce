import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

function DashboardPage() {
  const { user } = useAuth();
  const [recommendations, setRecommendations] = useState([]);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    // B2B categories with hosted images
    setCategories([
      {
        name: "Industrial Machinery",
        image: "https://images.unsplash.com/photo-1581090700227-4c4f50b3c6b3?auto=format&fit=crop&w=800&q=80",
      },
      {
        name: "Wholesale Electronics",
        image: "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=800&q=80",
      },
      {
        name: "Bulk Office Supplies",
        image: "https://images.unsplash.com/photo-1503602642458-232111445657?auto=format&fit=crop&w=800&q=80",
      },
      {
        name: "Construction Materials",
        image: "https://images.unsplash.com/photo-1581092330385-df93fd8b6241?auto=format&fit=crop&w=800&q=80",
      },
    ]);

    // B2B recommended products
    setRecommendations([
      {
        id: 1,
        title: "Pallet of Industrial Cleaning Supplies (500 units)",
        price: 1299.99,
        image: "https://images.unsplash.com/photo-1628512076771-4ef9f0a9b47a?auto=format&fit=crop&w=800&q=80",
      },
      {
        id: 2,
        title: "Wholesale Laptops (50 units)",
        price: 24999.99,
        image: "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&w=800&q=80",
      },
      {
        id: 3,
        title: "Construction Steel Beams (20 Tons)",
        price: 18999.99,
        image: "https://images.unsplash.com/photo-1608571429266-0c46faeef61f?auto=format&fit=crop&w=800&q=80",
      },
      {
        id: 4,
        title: "Office Chairs Bulk Order (200 units)",
        price: 15999.99,
        image: "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?auto=format&fit=crop&w=800&q=80",
      },
    ]);
  }, []);

  return (
    <div className="bg-gray-50 dark:bg-gray-900 min-h-screen">
      {/* Top Hero Section */}
      <div className="relative">
        <img
          src="https://images.unsplash.com/photo-1520607162513-77705c0f0d4a?auto=format&fit=crop&w=1920&q=80"
          alt="Hero Banner"
          className="w-full h-64 object-cover"
        />
        <div className="absolute inset-0 bg-black bg-opacity-50 flex flex-col justify-center items-center text-center">
          <h1 className="text-3xl md:text-5xl font-bold text-white">
            {user ? `Welcome back, ${user.name}` : "Your B2B Procurement Hub"}
          </h1>
          <p className="text-gray-200 mt-2">
            Streamline your wholesale & industrial purchases
          </p>
        </div>
      </div>

      {/* Categories */}
      <div className="max-w-7xl mx-auto px-4 py-6">
        <h2 className="text-2xl font-semibold mb-4 dark:text-white">
          Shop by Business Category
        </h2>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          {categories.map((cat) => (
            <Link
              to={`/category/${cat.name.toLowerCase().replace(/\s+/g, "-")}`}
              key={cat.name}
              className="bg-white dark:bg-gray-800 rounded-lg shadow hover:shadow-lg transition transform hover:-translate-y-1"
            >
              <img
                src={cat.image}
                alt={cat.name}
                className="w-full h-32 object-cover rounded-t-lg"
              />
              <div className="p-3 text-center">
                <p className="font-medium dark:text-white">{cat.name}</p>
              </div>
            </Link>
          ))}
        </div>
      </div>

      {/* Recommendations */}
      <div className="max-w-7xl mx-auto px-4 py-6">
        <h2 className="text-2xl font-semibold mb-4 dark:text-white">
          Recommended for Your Business
        </h2>
        <div className="flex gap-4 overflow-x-auto scrollbar-hide">
          {recommendations.map((item) => (
            <div
              key={item.id}
              className="flex-none w-64 bg-white dark:bg-gray-800 rounded-lg shadow hover:shadow-lg transition"
            >
              <img
                src={item.image}
                alt={item.title}
                className="w-full h-40 object-cover rounded-t-lg"
              />
              <div className="p-3">
                <p className="font-medium text-sm dark:text-white line-clamp-2">
                  {item.title}
                </p>
                <p className="text-blue-600 font-bold mt-2">
                  ${item.price.toLocaleString()}
                </p>
                <button className="mt-3 w-full bg-yellow-400 hover:bg-yellow-500 text-black px-3 py-1 rounded-lg font-medium">
                  Add to RFQ
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Footer */}
      <footer className="bg-gray-200 dark:bg-gray-800 py-6 mt-8 text-center text-sm text-gray-600 dark:text-gray-400">
        &copy; {new Date().getFullYear()} TradePro - Your B2B eCommerce Partner
      </footer>
    </div>
  );
}

export default DashboardPage;
