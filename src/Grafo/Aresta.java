package Grafo;


public class Aresta implements Comparable<Aresta>{

		private String rotulo;
		private Vertice v1;
		private Vertice v2;
		private int peso;
		
		public Aresta(Vertice v1, Vertice v2) {
			super();
			this.v1 = v1;
			this.v2 = v2;
		}
		
		public Aresta(Vertice v1, Vertice v2,int peso) {
			super();
			this.v1 = v1;
			this.v2 = v2;
			this.peso = peso;
		}
		
		public Aresta(int _peso) {
			peso = _peso;
		}
		
		public String getRotulo() {
			return rotulo;
		}
		public void setRotulo(String rotulo) {
			this.rotulo = rotulo;
		}
		public Vertice getV1() {
			return v1;
		}
		public void setV1(Vertice v1) {
			this.v1 = v1;
		}
		public Vertice getV2() {
			return v2;
		}
		public void setV2(Vertice v2) {
			this.v2 = v2;
		}
		public int getPeso() {
			return peso;
		}
		public void setPeso(int peso) {
			this.peso = peso;
		}
		
		
		public boolean contemAresta(Vertice v1,Vertice v2){
			if((this.v1.getNome() == v1.getNome())&&(this.v2.getNome() == v2.getNome()))
				return(true);
			if((this.v1.getNome() == v2.getNome())&&(this.v2.getNome() == v1.getNome())) 
				return(true);
			return(false);
		}
		public boolean contemVertice(Vertice v){
			if(this.v1.getNome() == v.getNome()) 
				return(true);
			if(this.v2.getNome() == v.getNome()) 
				return(true);
			return(false);
		}
		
		public boolean contemArestaDir(Vertice v1,Vertice v2){
			if((this.v1.getNome() == v1.getNome())&&(this.v2.getNome() == v2.getNome())) {
				return true;
			}
			return false;
		}
		public boolean contemVerticeDir(Vertice v){
			if(this.v1.getNome() == v.getNome()) 
				return(true);
			if(this.v2.getNome() == v.getNome()) 
				return(true);
			return(false);
		}
		
		@Override
		public String toString() {
			return "Aresta [ " +v1 +", "+ v2 + ", Peso=" + peso + " ]";
		}

		@Override
		public int compareTo(Aresta otherEdge) {				
			return this.getPeso() - otherEdge.getPeso();
		}
	
}
