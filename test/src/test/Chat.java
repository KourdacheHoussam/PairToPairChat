package test;

import inria.scifloware.sciflowarecomponent.command.Command;
import inria.smarttools.core.component.PropertyMap;

import java.util.ArrayList;

import test.view.IChatView;

import javax.swing.SwingUtilities;

import test.view.ChatFrame;

public abstract class Chat extends inria.scifloware.sciflowarecomponent.SciflowareComponentFacade {

	protected ArrayList<String> neighbours=new ArrayList<String>();
	protected IChatView view = null;
	protected ArrayList<Integer> QueriesId = new ArrayList<Integer>();
	
	public Chat () {
		

		Command Recherche = new Command() {

			@Override
			public void execute(){
				
				// Va envoyer une query pour les voisins de ses voisins
				getView().SearchNewNeighbours();
				getView().addConnu(neighbours);
				
			}

		};
		
		// Execute la query de recherche de voisins et affichage toutes les 10 secs
		trigger(10000, Recherche, true);
	}
	
	public void input( String expeditor, java.lang.String parameter) {
		getView().msg( parameter );
		
	}

	public abstract void output( java.lang.String parameter);

	public abstract void output( String destination, java.lang.String parameter);
	
	
	public IChatView getView() {
		if (view == null) {
			view = new ChatFrame(this);
		}
		return view;
	}

	@Override
	protected void neighbourJustConnected(String name, String service) {
		if (service.equals("chat")) {
			System.out.println(getIdName() + " can now talk to " + name);
			getView().msg("/**** " +getIdName() + " peut maintenant parler a " + name + " ****/");
			getView().addNeighbour(name);
			// Ajoute aussi le nouveau connecté dans neighboors
			if (!neighbours.contains(name))
			neighbours.add(name);
		}
	}
	
	public void disconnectInput(String expeditor) {
		// TODO Auto-generated method stub
		
	}

	public void shutdown(String expeditor) {
		// TODO Auto-generated method stub
		
	}

	public Object requestTree(String expeditor) {
		// TODO Auto-generated method stub
		return null;
	}

	public void quit(String expeditor) {
		// TODO Auto-generated method stub
		
	}

	public void receiveQueryAnswer(String expeditor, Integer id, String answer) {
		// Query Answer
		
	}
	
	public void addID(Integer id) {
		// Permet de stocker les ID de requêtes dèja traités
		QueriesId.add(id);
	}

	public void receiveQuery(String expeditor, String initiator, Integer id,
			String request, Integer ttl) {
		
		// Connection a l'initiateur
		//connectTo("ComponentsManager", getIdName() , null, initiator, "", "", "", new PropertyMap());
	
			
			
		
	}
	
	// M�thode appell�e quand le composant SON est pret
	@Override
	public void InitOn(){
		super.InitOn();
		
		// Initilisation de la vue quand le composant est pr�t
		if (view == null) {
			view = new ChatFrame(this);
		}
	}

	public void ReceiveQueryVoisins(String expeditor, String parameter) {
			
	}

	public void getVoisins(String expeditor, ArrayList<String> voisins) {
		
		// Recupere la liste des voisins du voisin
		for (int i=0;i<voisins.size();i++)
		{
			if (!neighbours.contains(voisins.get(i)) && voisins.get(i)!=this.getIdName())
					{
						neighbours.add(voisins.get(i));
					}
		}
		
	}
	
	public abstract void sendQuery(String adressee, String initiator, Integer id,
			String request, Integer ttl);
	
	public abstract void sendQueryAnswer(String adressee, Integer id, String answer);

	public abstract void sendVoisins( ArrayList<String> voisins);

	public abstract void sendVoisins( String destination, ArrayList<String> voisins);
	
	public abstract void SendQueryVoisins( java.lang.String parameter);

	public abstract void SendQueryVoisins( String destination, java.lang.String parameter);

}