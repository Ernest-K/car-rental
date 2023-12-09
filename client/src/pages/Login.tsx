import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/use-toast";
import useAuth from "@/hooks/useAuth";
import { User } from "@/types/types";
import { Button } from "@radix-ui/themes";
import { SubmitHandler, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const form = useForm<User>({
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit: SubmitHandler<User> = (data) => {
    fetch("http://localhost:8080/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        if (res.ok) {
          login(data);
          navigate("/dashboard");
          toast({
            title: "Login",
            description: "Login successfully",
          });
        } else {
          throw Error("Bad credentials");
        }
      })
      .catch((e) => {
        form.setError("username", { type: "value", message: "Bad username" });
        form.setError("password", { type: "value", message: "Bad password" });
      });
  };

  return (
    <div className="sm:grid gap-5 grid-cols-1 md:grid-cols-3 py-4">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="md:col-start-2 flex flex-col gap-2"
        >
          <FormField
            control={form.control}
            name="username"
            rules={{
              required: {
                value: true,
                message: "Please enter username",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Username</FormLabel>
                <FormControl>
                  <Input placeholder="username" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            rules={{
              required: {
                value: true,
                message: "Please enter password",
              },
            }}
            render={({ field }) => (
              <FormItem>
                <FormLabel className="text">Password</FormLabel>
                <FormControl>
                  <Input type="password" placeholder="password" {...field} />
                </FormControl>
                <FormMessage className="font-normal" />
              </FormItem>
            )}
          />

          <Button className="w-full" type="submit">
            Login
          </Button>
        </form>
      </Form>
    </div>
  );
}

export default Login;
