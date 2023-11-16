import { Reservation } from "@/types/interfaces";
import { Pencil2Icon, TrashIcon } from "@radix-ui/react-icons";
import { Button, Table } from "@radix-ui/themes";
import { useEffect, useState } from "react";
import { toast } from "./ui/use-toast";
import { Link } from "react-router-dom";

function ReservationTable() {
  const [reservations, setReservations] = useState<Reservation[]>([]);
  useEffect(() => {
    fetch("http://localhost:8080/api/reservations")
      .then((res) => res.json())
      .then((data) => setReservations(data));
  }, []);

  const deleteReservation = (id: number) => {
    fetch(`http://localhost:8080/api/reservations/${id}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (res.ok) {
          return res.text();
        }
      })
      .then((data) => {
        setReservations(
          reservations.filter((reservation) => reservation.id != id)
        );
        toast({
          title: "Reservation",
          description: data,
        });
      });
  };

  return !reservations.length ? (
    <p className="text-center p-3">No reservations</p>
  ) : (
    <Table.Root className="m-3" variant="surface">
      <Table.Header>
        <Table.Row>
          <Table.ColumnHeaderCell>Id</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Start date</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>End date</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Cost</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Driver</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell>Car</Table.ColumnHeaderCell>
          <Table.ColumnHeaderCell className="text-center">
            Operation
          </Table.ColumnHeaderCell>
        </Table.Row>
      </Table.Header>

      <Table.Body>
        {reservations.map((reservation) => (
          <Table.Row key={reservation.id}>
            <Table.RowHeaderCell>{reservation.id}</Table.RowHeaderCell>
            <Table.Cell>{reservation.startDate.toString()}</Table.Cell>
            <Table.Cell>{reservation.endDate.toString()}</Table.Cell>
            <Table.Cell>{reservation.cost}</Table.Cell>
            <Table.Cell>{`${reservation.driver.firstName} ${reservation.driver.lastName}`}</Table.Cell>
            <Table.Cell>
              {reservation.car &&
                `${reservation.car.make} ${reservation.car.model}`}
            </Table.Cell>
            <Table.Cell>
              <div className="w-full flex justify-evenly">
                <Button size={"3"} variant="ghost">
                  <Link to={`/dashboard/reservation/${reservation.id}/edit`}>
                    <Pencil2Icon width="16" height="16" />
                  </Link>
                </Button>
                <Button
                  size={"3"}
                  variant="ghost"
                  color="red"
                  onClick={() => deleteReservation(reservation.id)}
                >
                  <TrashIcon width="16" height="16" />
                </Button>
              </div>
            </Table.Cell>
          </Table.Row>
        ))}
      </Table.Body>
    </Table.Root>
  );
}

export default ReservationTable;
