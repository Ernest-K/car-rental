import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from "./layout/Layout";
import Home from "./pages/Home";
import Detail from "./pages/cars/Detail";
import Reservation from "./pages/cars/Reservation";
import Dashboard from "./pages/dashboard/Dashboard";
import EditReservation from "./pages/dashboard/reservation/EditReservation";
import EditCar from "./pages/dashboard/car/EditCar";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="cars/:id" element={<Detail />} />
          <Route path="cars/:id/reservation" element={<Reservation />} />
          <Route path="dashboard/" element={<Dashboard />} />
          <Route
            path="dashboard/reservation/:id/edit"
            element={<EditReservation />}
          />
          <Route path="dashboard/car/create" element={<Dashboard />} />
          <Route path="dashboard/car/:id/edit" element={<EditCar />} />
          <Route path="dashboard/category/:id/add" element={<Dashboard />} />
          <Route path="dashboard/category/:id/edit" element={<Dashboard />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
