import { Outlet } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";
import { Toaster } from "@/components/ui/toaster";

function Layout() {
  return (
    <div>
      <Header />
      <main>
        <Outlet />
      </main>
      <Toaster />
      <Footer />
    </div>
  );
}

export default Layout;
