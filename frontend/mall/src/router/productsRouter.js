import React, { Suspense, lazy } from 'react';
import { Navigate } from 'react-router-dom';

const Loading = <div>Loading...</div>
const ProductsList = lazy(() => import("../pages/products/ListPage"))
const ProductsAdd = lazy(() => import("../pages/products/AddPage"))

const productsRouter = () => {
  return [
    {
      path: "",
      element: <Navigate replace to={'/products/list'}></Navigate>
    },
    {
      path: "list",
      element: <Suspense fallback={Loading}><ProductsList /></Suspense>
    },
    {
      path: "add",
      element: <Suspense fallback={Loading}><ProductsAdd /></Suspense>
    },

  ];
};

export default productsRouter;