import ReservationTable from "@/components/ReservationTable";
import { Container, Tabs } from "@radix-ui/themes";

function Dashboard() {
  return (
    <Container>
      <Tabs.Root defaultValue="reservations">
        <Tabs.List>
          <Tabs.Trigger value="reservations">Reservations</Tabs.Trigger>
          <Tabs.Trigger value="cars">Cars</Tabs.Trigger>
          <Tabs.Trigger value="categories">Categories</Tabs.Trigger>
        </Tabs.List>

        <Tabs.Content value="reservations">
          <ReservationTable />
        </Tabs.Content>
      </Tabs.Root>
    </Container>
  );
}

export default Dashboard;
