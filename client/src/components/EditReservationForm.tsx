import { SubmitHandler, useForm } from "react-hook-form";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "./ui/form";
import { Reservation } from "@/types/interfaces";
import { useEffect, useState } from "react";
import { EditReservationInput } from "@/types/types";
import { useNavigate, useParams } from "react-router-dom";
import { Popover, PopoverContent, PopoverTrigger } from "./ui/popover";
import { Button } from "@radix-ui/themes";
import { cn } from "@/lib/utils";
import { format } from "date-fns";
import { CalendarIcon } from "@radix-ui/react-icons";
import { Calendar } from "./ui/calendar";
import { Input } from "./ui/input";
import { calcTotalPrice, prepareReservationRequest } from "@/utils";
import { toast } from "./ui/use-toast";
import useAuth from "@/hooks/useAuth";

function EditReservationForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [reservation, setReservation] = useState<Reservation>();
  const { getUser } = useAuth();

  useEffect(() => {
    fetch(`http://localhost:8080/api/reservations/${id}`, {
      headers: {
        Authorization: `Basic ${getUser()}`,
      },
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        }
      })
      .then((data: Reservation) => {
        setReservation(data);
        setValues(data);
      });
  }, [id]);

  const setValues = (reservation: Reservation) => {
    form.setValue("startDate", new Date(reservation.startDate));
    form.setValue("endDate", new Date(reservation.endDate));
    form.setValue("firstName", reservation.driver.firstName);
    form.setValue("lastName", reservation.driver.lastName);
    form.setValue("email", reservation.driver.email);
    form.setValue("firstName", reservation.driver.firstName);
    form.setValue("phoneNumber", reservation.driver.phoneNumber);
    form.setValue("cost", reservation.cost);
  };

  const form = useForm<EditReservationInput>({
    defaultValues: {
      startDate: new Date(),
      endDate: new Date(),
      firstName: "",
      lastName: "",
      email: "",
      phoneNumber: "",
      cost: 0.0,
    },
  });

  const startDateWatch = form.watch("startDate");
  const endDateWatch = form.watch("endDate");

  const onSubmit: SubmitHandler<EditReservationInput> = (data) => {
    fetch(`http://localhost:8080/api/reservations/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Basic ${getUser()}`,
      },
      body: prepareReservationRequest(data),
    })
      .then((res) => {
        if (res.ok) {
          toast({
            title: "Reservation",
            description: "Reservation update has been processed successfully",
          });
          navigate("/dashboard");
        } else {
          return res.json();
        }
      })
      .then((data) => {
        toast({
          variant: "destructive",
          title: "Reservation",
          description: data.errors.join(", "),
        });
      });
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="md:col-start-2 flex flex-col gap-2"
      >
        <div className="flex flex-col sm:flex-row gap-3">
          <FormField
            control={form.control}
            name="startDate"
            rules={{ required: true }}
            render={({ field }) => (
              <FormItem className="basis-1/2">
                <FormLabel>Start date</FormLabel>
                <Popover>
                  <PopoverTrigger asChild>
                    <FormControl>
                      <Button
                        color="gray"
                        variant={"outline"}
                        className={cn("w-full pl-3 text-left font-normal")}
                      >
                        {field.value ? (
                          format(field.value, "PP")
                        ) : (
                          <span>Pick a date</span>
                        )}
                        <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                      </Button>
                    </FormControl>
                  </PopoverTrigger>
                  <PopoverContent className="w-auto p-0" align="start">
                    <Calendar
                      mode="single"
                      selected={field.value}
                      onSelect={(e) => {
                        if (e && endDateWatch.getTime() < e.getTime()) {
                          field.onChange(e);
                          form.setValue("endDate", e);
                        } else {
                          field.onChange(e);
                        }
                      }}
                      disabled={{ before: new Date() }}
                      initialFocus
                      required
                    />
                  </PopoverContent>
                </Popover>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="endDate"
            render={({ field }) => (
              <FormItem className="basis-1/2">
                <FormLabel>End date</FormLabel>
                <Popover>
                  <PopoverTrigger asChild>
                    <FormControl>
                      <Button
                        color="gray"
                        variant={"outline"}
                        className={cn("w-full pl-3 text-left font-normal")}
                      >
                        {field.value ? (
                          format(field.value, "PP")
                        ) : (
                          <span>Pick a date</span>
                        )}
                        <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                      </Button>
                    </FormControl>
                  </PopoverTrigger>
                  <PopoverContent className="w-auto p-0" align="start">
                    <Calendar
                      mode="single"
                      selected={field.value}
                      onSelect={field.onChange}
                      disabled={{ before: startDateWatch }}
                      initialFocus
                      required
                    />
                  </PopoverContent>
                </Popover>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <FormField
          control={form.control}
          name="firstName"
          rules={{
            required: {
              value: true,
              message: "Please enter your first name",
            },
          }}
          render={({ field }) => (
            <FormItem>
              <FormLabel className="text">First name</FormLabel>
              <FormControl>
                <Input placeholder="John" {...field} />
              </FormControl>
              <FormMessage className="font-normal" />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="lastName"
          rules={{
            required: {
              value: true,
              message: "Please enter your last name",
            },
          }}
          render={({ field }) => (
            <FormItem>
              <FormLabel>Last name</FormLabel>
              <FormControl>
                <Input placeholder="Doe" {...field} />
              </FormControl>
              <FormMessage className="font-normal" />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="email"
          rules={{
            required: {
              value: true,
              message: "Please enter your email",
            },
          }}
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input type="email" placeholder="john@doe.com" {...field} />
              </FormControl>
              <FormMessage className="font-normal" />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="phoneNumber"
          rules={{
            required: {
              value: true,
              message: "Please enter your phone number",
            },
            pattern: {
              value: /^[0-9]*$/,
              message: "Phone number is not valid",
            },
          }}
          render={({ field }) => (
            <FormItem>
              <FormLabel>Phone number</FormLabel>
              <FormControl>
                <Input maxLength={9} placeholder="123456789" {...field} />
              </FormControl>
              <FormMessage className="font-normal" />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="cost"
          rules={{
            required: {
              value: true,
              message: "Please enter your cost",
            },
          }}
          render={({ field }) => (
            <FormItem>
              <FormLabel>Cost</FormLabel>
              <FormControl>
                <Input placeholder="21.37" {...field} />
              </FormControl>
              <FormMessage className="font-normal" />
            </FormItem>
          )}
        />
        {reservation?.car && (
          <div className="flex flex-col justify-center items-center">
            <p className="font-light text-sm">Total price</p>
            <p className="">
              {calcTotalPrice(
                reservation.car.price,
                startDateWatch,
                endDateWatch
              )}{" "}
              PLN
            </p>
          </div>
        )}

        <Button type="submit">Save</Button>
      </form>
    </Form>
  );
}

export default EditReservationForm;
