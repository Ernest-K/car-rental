import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from "./components/Layout";
import Home from "./pages/Home";
import CarDetail from "./pages/cars/CarDetail";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="cars/:id" element={<CarDetail />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
