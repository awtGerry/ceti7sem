pub mod passwd {
    pub struct User {
        pub username: String,
        pub password: String,
    }

    impl User {
        pub fn new(username: &str, password: &str) -> User {
            User {
                username: username.to_string(),
                password: password.to_string(),
            }
        }
    }
}

#[derive(Debug)]
pub enum PasswordError {
    TooShort,
    NoUppercase,
    NoLowercase,
    NoNumber,
    NoSpecial,
}

pub fn check_password(password: &str) -> Result<(), PasswordError> {
    let mut has_uppercase = false;
    let mut has_lowercase = false;
    let mut has_number = false;
    let mut has_special = false;

    match password.len() {
        0..=7 => return Err(PasswordError::TooShort),
        _ => {
            for c in password.chars() {
                if c.is_uppercase() {
                    has_uppercase = true;
                } else if c.is_lowercase() {
                    has_lowercase = true;
                } else if c.is_numeric() {
                    has_number = true;
                } else {
                    has_special = true;
                }
            }
        }
    }
    
    let res = has_uppercase && has_lowercase && has_number && has_special;
    if !res {
        if !has_uppercase {
            return Err(PasswordError::NoUppercase);
        } else if !has_lowercase {
            return Err(PasswordError::NoLowercase);
        } else if !has_number {
            return Err(PasswordError::NoNumber);
        } else {
            return Err(PasswordError::NoSpecial);
        }
    }

    Ok(())
}
