import CarTable from "@/components/CarTable";
import CategoryTable from "@/components/CategoryTable";
import ReservationTable from "@/components/ReservationTable";
import useAuth from "@/hooks/useAuth";
import { ExitIcon, PlusIcon } from "@radix-ui/react-icons";
import { Button, Container, Tabs } from "@radix-ui/themes";
import { Link, Navigate } from "react-router-dom";

function Dashboard() {
  const { isLogged, logout } = useAuth();

  if (!isLogged()) {
    return <Navigate to={"/auth/login"} />;
  }

  return (
    <Container>
      <div className="flex justify-end px-5 py-3">
        <Button
          asChild
          variant="solid"
          onClick={logout}
          className="flex justify-center items-center"
        >
          <Link
            to={"/auth/login"}
            relative="path"
            className="flex items-center gap-1"
          >
            Logout
            <ExitIcon />
          </Link>
        </Button>
      </div>
      <Tabs.Root defaultValue="reservations">
        <Tabs.List>
          <Tabs.Trigger value="reservations">Reservations</Tabs.Trigger>
          <Tabs.Trigger value="cars">Cars</Tabs.Trigger>
          <Tabs.Trigger value="categories">Categories</Tabs.Trigger>
        </Tabs.List>

        <Tabs.Content value="reservations">
          <ReservationTable />
        </Tabs.Content>
        <Tabs.Content value="cars">
          <CarTable />
          <div className="flex justify-end px-3 pb-3">
            <Button
              asChild
              variant="soft"
              className="flex justify-center items-center"
            >
              <Link
                to={"/dashboard/car/add"}
                relative="path"
                className="flex items-center gap-1"
              >
                Add
                <PlusIcon />
              </Link>
            </Button>
          </div>
        </Tabs.Content>
        <Tabs.Content value="categories">
          <CategoryTable />
          <div className="flex justify-end px-3 pb-3">
            <Button
              asChild
              variant="soft"
              className="flex justify-center items-center"
            >
              <Link
                to={"/dashboard/category/add"}
                relative="path"
                className="flex items-center gap-1"
              >
                Add
                <PlusIcon />
              </Link>
            </Button>
          </div>
        </Tabs.Content>
      </Tabs.Root>
    </Container>
  );
}

export default Dashboard;
