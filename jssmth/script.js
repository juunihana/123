$(document).ready(() => {
    $('#usernameField').append('username');

    $('#hello1').click(() => {
        $('#hello1_block').toggleClass('hidden');
    });

    $('#upvote').click(() => {
        incrementLabel('#upvoteLabel');
    });

    $('#downvote').click(() => {
        incrementLabel('#downvoteLabel');
    });

    $('#hiddenEl').click(() => {
        $('#hiddenEl_container').toggleClass('hidden-element_toggle');
    });

    $('#inputText').on('input', () => {
        $('#inputTextLabel').text($('#inputText').val());
    });

    $('#fetchData').click(() => {
        let username = $('#usernameField').val();
        console.log(username);
        if (!(!username || username == '')) {
            $.ajax({
                url: 'http://localhost:8081/api/v1/endpoint?username=' + username,
                method: 'get',
                dataType: 'json',
                crossDomain: true,
                headers: {
                    'Access-Control-Allow-Origin': '*'
                },
                success: (data) => {
                    console.log(data);
                    $('#fetchDataText').text(data.greeting);
                }
            });
        }
    });
});

function incrementLabel(elementId) {
    $(elementId).text((i, oldText) => {
        $(elementId).text(parseInt(oldText) + 1);
    });
}

