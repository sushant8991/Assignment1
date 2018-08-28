public class fixBookControl { //Method case should be in camelcase //
	
	private FixBookUi ui; \\Since the variable name should be in camel case and be meaingful(FixBookUI to FixBookUi)
	private enum CONTROL_STATE { INITIALISED, READY, FIXING };
	private CONTROL_STATE state;
	
	private library library; //Changed from L to Library	
	private book currentBook;


	public FixBookControl() {
		this.library = library.INSTANCE();
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUi(FixBookUi ui) \\The name of the method should be in camel case as well(setUI to setUi)
        {
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(FixBookUi.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}


	public void BookScanned(int BookId) \\Since the class name should be in camel case therefore converting (bookId to BookId) 
        {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		currentBook = library.Book(BookId);
		
		if (currentBook == null) {
			ui.display("Invalid BookId");
			return;
		}
		if (!currentBook.Damaged()) {
			ui.display("\"Book has not been damaged");
			return;
		}
		ui.display(currentBook.toString());
		ui.setState(FixBookUi.UI_STATE.FIXING); \\Changed FixBookUI to FixBookUi
		state = CONTROL_STATE.FIXING;		
	}


	public void fixBook(boolean fix) {
		if (!state.equals(CONTROL_STATE.FIXING)) {
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (fix) {
			library.repairBook(currentBook);
		}
		currentBook = null;
		ui.setState(FixBookUi.UI_STATE.READY); \\Changed FixBookUI to FixBookUi
		state = CONTROL_STATE.READY;		
	}

	
	public void scanningComplete() {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.setState(FixBookUi.UI_STATE.COMPLETED); \\Changed FixBookUI to FixBookUi		
	}






}
