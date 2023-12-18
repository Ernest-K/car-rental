import { Category } from "@/types/interfaces";
import { EditCarInput } from "@/types/types";
import { useEffect, useState } from "react";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "./ui/form";
import { Input } from "./ui/input";
import { Button } from "@radix-ui/themes";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "./ui/select";
import { toast } from "./ui/use-toast";
import useAuth from "@/hooks/useAuth";

function EditCarForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [categories, setCategories] = useState<Category[]>([]);
  const { getUser } = useAuth();

  useEffect(() => {
    fetch(`http://localhost:8080/api/categories`)
      .then((res) => {
        if (res.ok) {
          return res.json();
        }
      })
      .then((data: Category[]) => {
        setCategories(data);
      });
  }, [id]);

  const form = useForm<EditCarInput>({
    defaultValues: {
      make: "",
      model: "",
      productionYear: 0,
      power: 0,
      priceForDay: 0,
      priceForTwoToFourDays: 0,
      priceForWeek: 0,
    },
  });

  const onSubmit: SubmitHandler<EditCarInput> = (data) => {
    fetch(`http://localhost:8080/api/cars`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Basic ${getUser()}`,
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        if (res.ok) {
          toast({
            title: "Car",
            description: "Car add has been processed successfully",
          });
          navigate("/dashboard");
        } else {
          return res.json();
        }
      })
      .then((data) => {
        toast({
          variant: "destructive",
          title: "Car",
          description: data.errors.join(", "),
        });
      });
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="grid grid-cols-1 md:grid-cols-2 gap-5"
      >
        <div className="flex flex-col gap-2">
          <FormField
            control={form.control}
            name="status"
            rules={{
              required: {
                value: true,
                message: "Please enter status",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Status</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select status" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="AVAILABLE">AVAILABLE</SelectItem>
                    <SelectItem value="UNAVAILABLE">UNAVAILABLE</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="make"
            rules={{
              required: {
                value: true,
                message: "Please enter car make",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Make</FormLabel>
                <FormControl>
                  <Input placeholder="Car make" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="model"
            rules={{
              required: {
                value: true,
                message: "Please enter car model",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Model</FormLabel>
                <FormControl>
                  <Input placeholder="Car model" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="productionYear"
            rules={{
              required: {
                value: true,
                message: "Please enter production year",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Production year</FormLabel>
                <FormControl>
                  <Input type="year" placeholder="Production year" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="power"
            rules={{
              required: {
                value: true,
                message: "Please enter car power",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Power</FormLabel>
                <FormControl>
                  <Input type="year" placeholder="Power" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="fuelType"
            rules={{
              required: {
                value: true,
                message: "Please enter fuel type",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Fuel type</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select fuel type" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="PETROL">PETROL</SelectItem>
                    <SelectItem value="DIESEL">DIESEL</SelectItem>
                    <SelectItem value="HYBRID">HYBRID</SelectItem>
                    <SelectItem value="ELECTRIC">ELECTRIC</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="transmissionType"
            rules={{
              required: {
                value: true,
                message: "Please enter transmission type",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Transmission type</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select transmission type" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="MANUAL">MANUAL</SelectItem>
                    <SelectItem value="AUTOMATIC">AUTOMATIC</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="driveType"
            rules={{
              required: {
                value: true,
                message: "Please enter drive type",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Drive type</FormLabel>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <FormControl>
                    <SelectTrigger>
                      <SelectValue placeholder="Select drive type" />
                    </SelectTrigger>
                  </FormControl>
                  <SelectContent>
                    <SelectItem value="REAR">REAR</SelectItem>
                    <SelectItem value="FRONT">FRONT</SelectItem>
                    <SelectItem value="ALL_WHEEL">ALL WHEEL</SelectItem>
                  </SelectContent>
                </Select>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          {categories && (
            <FormField
              control={form.control}
              name="categoryId"
              rules={{
                required: {
                  value: true,
                  message: "Please enter category",
                },
              }}
              render={({ field }) => (
                <FormItem>
                  <FormLabel className="text">Category</FormLabel>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value?.toString()}
                  >
                    <FormControl>
                      <SelectTrigger>
                        <SelectValue placeholder="Select category" />
                      </SelectTrigger>
                    </FormControl>
                    <SelectContent>
                      {categories.length &&
                        categories.map((category) => (
                          <SelectItem
                            key={category.id}
                            value={category.id.toString()}
                          >
                            {category.name}
                          </SelectItem>
                        ))}
                    </SelectContent>
                  </Select>
                  <FormMessage className="font-normal" />
                </FormItem>
              )}
            />
          )}
        </div>
        <div className="flex flex-col gap-2">
          <FormField
            control={form.control}
            name="priceForDay"
            rules={{
              required: {
                value: true,
                message: "Please enter price for day",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Price for day</FormLabel>
                <FormControl>
                  <Input type="year" placeholder="Price for day" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="priceForTwoToFourDays"
            rules={{
              required: {
                value: true,
                message: "Please enter price for two to four days",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Price for 2-4 days</FormLabel>
                <FormControl>
                  <Input
                    type="year"
                    placeholder="Price for 2-4 days"
                    {...field}
                  />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <FormField
            control={form.control}
            name="priceForWeek"
            rules={{
              required: {
                value: true,
                message: "Please enter price for week",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Price for week</FormLabel>
                <FormControl>
                  <Input type="year" placeholder="Price for week" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <Button className="w-full !mt-5" type="submit">
            Save
          </Button>
        </div>
      </form>
    </Form>
  );
}

export default EditCarForm;
