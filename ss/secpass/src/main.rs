use iced::widget::{
    column,
    row,
    container,
    TextInput,
    text,
};

use iced::window;
use iced::Application;
use iced::{Theme, Command, Settings, Element};

fn main() -> iced::Result {
    SecPassApp::run(Settings {
        window: window::Settings {
            size: iced::Size {
                width: 1000.0,
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
}

#[derive(Debug, Clone)]
enum App {
    UserChanged(String),
    PasswordChanged(String),
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
        }
    }

    fn view(&self) -> Element<'_, Self::Message> {
        let header = {
            row![
                text("Login")
                    .width(iced::Length::Fill)
                    .size(32)
                    .style(iced::Color::from_rgb(227.0, 253.0, 253.0))
                    .horizontal_alignment(iced::alignment::Horizontal::Center),
            ].spacing(20)
        };

        let inputs = {
            let user_input = TextInput::new("󰁥 Enter email", &self.user_value)
                .on_input(App::UserChanged)
                .width(500)
                .padding(10);
            let passwd_input = TextInput::new(" Enter password", &self.input_value)
                .secure(true)
                .on_input(App::PasswordChanged)
                .width(500)
                .padding(10);
            column![
                user_input,
                passwd_input,
            ].spacing(20)
        };

        let content = column![
                header,
                inputs,
        ]
        .padding(20)
        .spacing(20)
        .align_items(iced::Alignment::Center);

        container(content).width(iced::Length::Fill).height(iced::Length::Fill).into()
    }

    fn theme(&self) -> Theme {
        Theme::GruvboxDark
    }
}

/* fn icon<'a>(codepoint: char) -> Element<'a, App> {
    const ICON_FONT: Font = Font::with_name("icon-font");
    text(codepoint).font(ICON_FONT).into()
} */
