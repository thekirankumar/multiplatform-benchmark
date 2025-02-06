import React, { useState, useEffect, useCallback } from 'react';
import { FlatList, View, ActivityIndicator, StyleSheet, Text, Image } from 'react-native';
import axios from 'axios';

export interface Product {
  id: number;
  title: string;
  description: string;
  price: number;
  thumbnail: string;
}

export interface ProductsResponse {
  products: Product[];
}


const BASE_URL = 'https://dummyjson.com/products';

export const fetchProducts = async (limit: number, skip: number): Promise<Product[]> => {
  try {
    const response = await axios.get<ProductsResponse>(`${BASE_URL}?limit=${limit}&skip=${skip}`);
    return response.data.products;
  } catch (error) {
    console.error('Error fetching products:', error);
    return [];
  }
};


const PAGE_SIZE = 30; // Number of items to load per page

const App: React.FC = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isEndReached, setIsEndReached] = useState<boolean>(false);
  const [currentPage, setCurrentPage] = useState<number>(0);

  // Fetch initial data
  useEffect(() => {
    loadMoreProducts();
  }, []);

  // Load more products when the user scrolls to the bottom
  const loadMoreProducts = useCallback(async () => {
    if (isLoading || isEndReached) return;

    setIsLoading(true);

    const newProducts = await fetchProducts(PAGE_SIZE, currentPage * PAGE_SIZE);

    if (newProducts.length > 0) {
      setProducts((prevProducts) => [...prevProducts, ...newProducts]);
      setCurrentPage((prevPage) => prevPage + 1);
    } else {
      setIsEndReached(true); // No more products to load
    }

    setIsLoading(false);
  }, [isLoading, isEndReached, currentPage]);

  // Render each product item
  const renderItem = ({ item }: { item: Product }) => (
    <View style={styles.itemContainer}>
        <Image
        fadeDuration={0}
        style={styles.image}
        source={{
          uri: item.thumbnail,
        }}
      />

      <Text style={styles.title}>{item.title}</Text>
    </View>
  );

  // Render a loading indicator at the bottom
  const renderFooter = () => {
    if (!isLoading) return null;
    return (
      <View style={styles.footer}>
        <ActivityIndicator size="large" color="#0000ff" />
      </View>
    );
  };

  return (
    <FlatList
      data={products}
      renderItem={renderItem}
      keyExtractor={(item) => item.id.toString()}
      numColumns={2} // Grid layout
      onEndReached={loadMoreProducts} // Triggered when the user scrolls to the bottom
      onEndReachedThreshold={0.5} // Load more when 50% from the bottom
      ListFooterComponent={renderFooter} // Show loading indicator
      contentContainerStyle={styles.container}
    />
  );
};

// Styles
const styles = StyleSheet.create({
  container: {
    padding: 8,
  },
  itemContainer: {
    flex: 1,
    margin: 4,
    alignItems: 'center',
  },
  image: {
    width: 128,
    height: 128
  },
  title: {
    marginTop: 8,
    fontSize: 14,
    textAlign: 'center',
  },
  footer: {
    padding: 16,
    alignItems: 'center',
  },
});

export default App;