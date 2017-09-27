package minesweeper.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import minesweeper.Board;

public class MinesweeperServerRunnable implements Runnable {
    private Socket socket = null;
    private Board b;
    private MinesweeperServer m;

    public MinesweeperServerRunnable(Socket s, Board b, MinesweeperServer m) {
        this.socket = s;
        this.b = b;
        this.m = m;
    }

    @Override
    public void run() {
        try {
            handleConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handle a single client connection. Returns when client disconnects.
     * 
     * @param socket
     *            socket where the client is connected
     * @throws IOException
     *             if the connection encounters an error or terminates
     *             unexpectedly
     */
    private void handleConnection() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.format(
                "Welcome to Minesweeper. Players: %d including you. Board: %d columns by %d rows. Type 'help' for help.",
                m.getNumberPlayers(), b.getColumnSize(), b.getRowSize());
        out.println();

        try {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String output = handleRequest(line);
                if (output.equals("bye")) {
                    // TODO: Consider improving spec of handleRequest to avoid
                    // use of null
                    break;
                } else {
                    out.println(output);
                }
            }
        } finally {
            out.close();
            in.close();
            m.setNumberPlayers(m.getNumberPlayers() - 1);
        }
    }

    /**
     * Handler for client input, performing requested operations and returning
     * an output message.
     * 
     * @param input
     *            message from client
     * @return message to client, or null if none
     */
    private String handleRequest(String input) {
        String regex = "(look)|(help)|(bye)|" + "(dig -?\\d+ -?\\d+)|(flag -?\\d+ -?\\d+)|(deflag -?\\d+ -?\\d+)";
        if (!input.matches(regex)) {
            // invalid input
            // TODO Problem 5
            return "Please read the manual!";
        }
        String[] tokens = input.split(" ");
        if (tokens[0].equals("look")) {
            // 'look' request
            // TODO Problem 5
            return b.toString();
        } else if (tokens[0].equals("help")) {
            // 'help' request
            return "Please read the manual!";
        } else if (tokens[0].equals("bye")) {
            // 'bye' request
            return "bye";
        } else {
            int x = Integer.parseInt(tokens[1]);
            int y = Integer.parseInt(tokens[2]);
            if (x < 0 || y < 0 || x >= b.getColumnSize() || y >= b.getRowSize()) {
                return b.toString();
            }
            if (tokens[0].equals("dig")) {
                // 'dig x y' request
                // TODO Problem 5
                if (b.getSquareList().get(b.getListIndex(x, y)).hasBomb()) {
                   b.dig(x, y);
                   if (m.getDebugMode()) {
                       return b.toString();
                   } else {
                       return "bye";
                   }
                } else {
                    b.dig(x, y);
                    return b.toString();
                }
            } else if (tokens[0].equals("flag")) {
                // 'flag x y' request
                // TODO Problem 5
                b.flag(x, y);
                return b.toString();
            } else if (tokens[0].equals("deflag")) {
                // 'deflag x y' request
                // TODO Problem 5
                b.deflag(x, y);
                return b.toString();
            }
        }
        // TODO: Should never get here, make sure to return in each of the cases
        // above
        throw new UnsupportedOperationException();
    }

}