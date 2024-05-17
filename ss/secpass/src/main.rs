/* Purpose:
   This program is a GUI to register an account with a email and password.
   The main objective is to make the user create a secure password that
   has a minimum of 8 characters, at least one uppercase letter, one lowercase
   one number and one special character. Also, the password is going to be
   saved in a database with the password encrypted.

   The program will also have a login page to authenticate the user, to test the
   decryption of the password.
*/

#[allow(unused_imports)]
use iced::widget::{
    column,
    row,
    button,
    Container,
    container,
    image,
    Image,
    checkbox,
    TextInput,
    text,
};

use iced::window;
use iced::Application;
use iced::{Theme, Command, Settings, Element};

mod passwd;
use crate::passwd::passwd::User;

fn main() -> iced::Result {
    SecPassApp::run(Settings {
        window: window::Settings {
            size: iced::Size {
                width: 800.0,
                height: 600.0,
            },
            resizable: false,
            ..window::Settings::default()
        },
        /* fonts: vec![include_bytes!("../fonts/icon-font.ttf")
            .as_slice()
            .into()
        ], */
        ..Settings::default()
    })
}

struct SecPassApp {
    user_value: String,
    input_value: String,
    error_msg: String,
}

#[derive(Debug, Clone)]
enum App {
    UserChanged(String),
    PasswordChanged(String),
    Login,
    Register,
}

impl Application for SecPassApp {
    type Message = App; // Messages that can be sent to the app

    type Theme = Theme; // Custom theme (use default dark for now)
    type Executor = iced::executor::Default; // engine to run async tasks
    type Flags = (); // data passed to the app on startup

    fn new(_flags: Self::Flags) -> (Self, Command<App>) {
        (
            Self {
                user_value: String::new(),
                input_value: String::new(),
                error_msg: String::new(),
            },
            Command::none()
        )
    }

    fn title(&self) -> String {
        String::from("Secure Password")
    }

    fn update(&mut self, message: Self::Message) -> Command<App> {
        match message {
            App::UserChanged(value) => {
                self.user_value = value;
                Command::none()
            }
            App::PasswordChanged(value) => {
                self.input_value = value;
                Command::none()
            }
            App::Login => {
                let user = User::new(&self.user_value, &self.input_value);
                if let Err(e) = passwd::check_password(&user.password) {
                    match e {
                        passwd::PasswordError::TooShort => {
                            self.error_msg = String::from("Password needs to have at least 8 characters");
                        }
                        passwd::PasswordError::NoUppercase => {
                            self.error_msg = String::from("Password has no uppercase letter");
                        }
                        passwd::PasswordError::NoLowercase => {
                            self.error_msg = String::from("Password has no lowercase letter");
                        }
                        passwd::PasswordError::NoNumber => {
                            self.error_msg = String::from("Password needs to have at least one number");
                        }
                        passwd::PasswordError::NoSpecial => {
                            self.error_msg = String::from("Password needs to have at least one special character");
                        }
                    }
                } else {
                    self.error_msg = String::from("");
                    println!("User: {}, Password: {}", user.username, user.password);
                }
                Command::none()
            }
            App::Register => {
                Command::none()
            }
        }
    }

    fn view(&self) -> Element<'_, Self::Message> {
        let header = {
            column![
                call_image("cat.png", 240),
                text("Login to your account")
                    .size(24)
                    .width(iced::Length::Fill)
                    .horizontal_alignment(iced::alignment::Horizontal::Center)
            ].spacing(10)
        };

        let user_fields = {
            let user_input = TextInput::new("󰁥  Enter email", &self.user_value)
                .on_input(App::UserChanged)
                .width(460)
                .padding(10);
            let passwd_input = TextInput::new("  Enter password", &self.input_value)
                .secure(true)
                .on_input(App::PasswordChanged)
                .width(460)
                .padding(10);
            let error_msg = text(&self.error_msg).style(iced::Color::from_rgb8(210, 15, 57)).size(12);

            // Separate the inputs with a 20px space between them
            let inputs = column![ user_input, passwd_input ].spacing(20);

            column![
                inputs,
                error_msg
            ].spacing(5)
        };

        let login_button = {
            column![
                button("Login")
                    .on_press_maybe(
                        if self.user_value.is_empty() || self.input_value.is_empty() {
                            None
                        } else {
                            Some(App::Login)
                        }
                    )
                    .width(460)
                    .padding([10, 20]),

                button("Don't have an account? Sign up")
                    .on_press(App::Register)
                    .width(460)
                    .style(iced::theme::Button::Text)
            ].spacing(5)
        };

        let content = column![
                header,
                user_fields,
                login_button
        ]
        .padding(20)
        .spacing(20)
        .align_items(iced::Alignment::Center);

        container(content).width(iced::Length::Fill).height(iced::Length::Fill).into()
    }

    fn theme(&self) -> Theme {
        Theme::CatppuccinLatte
    }
}

fn call_image<'a>(file_name: &str, width: u16) -> Container<'a, App> {
    container(
        image(format!("assets/{file_name}")).width(width),
    )
        .width(iced::Length::Fill)
        .center_x()
}

/* fn icon<'a>(codepoint: char) -> Element<'a, App> {
    const ICON_FONT: Font = Font::with_name("icon-font");
    text(codepoint).font(ICON_FONT).into()
} */
