export function errorMessage(error) {
    let mess = "";
    if (error.response) {
        if (error.response.data.error) {
            return error.response.data.error;
        }

        if (error.response.data && error.response.data.errors.length) {
            return error.response.data.errors[0].message;
        }

        const status = error.response.status;
        console.log(status);
        if (status < 500) {
            if (status === 403) {
                mess = "User with username seems to exit";
            } else {
                mess = "We are having problems connecting to the server";
            }
        } else if (status < 600) {
            mess = "Our server seems to be down.. Hold on tight!";
        }
    } else if (error.request) {
        mess = "There seems to be a problem with our servers...";
    } else {
        mess = "Unknown error occurred";
    }
    return mess;
}