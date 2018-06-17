import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DAO<T> {
	private String filename;

	public DAO(String filename) {
		this.filename = filename;
	}
	
	public void save(T t) throws FileNotFoundException, IOException {
		ObjectOutputStream out;
		try {
			File f = new File(filename);
			if (f.isFile()) {
				out = new AppendingObjectOutputStream(new FileOutputStream(f, true));
			} else {
				out = new ObjectOutputStream(new FileOutputStream(f));
			}
			out.writeObject(t);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
	
	public List<T> findAll() throws FileNotFoundException, IOException, ClassNotFoundException {
		List<T> list = new ArrayList<>();
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			while (true) {
				list.add((T) in.readObject());
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (EOFException e) {
			in.close();
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			in.close();
			throw e;		
		} 
		return list;
	}	

}

class AppendingObjectOutputStream extends ObjectOutputStream {

	public AppendingObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		reset();
	}

}

