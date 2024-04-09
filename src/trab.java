import java.io.*;
import java.util.Scanner;

//objetivo: fazer um app de mascaras infantil e adultas, conferir se tem no estoque para venda e guardar as vendas do dia calcular lucro e tirar do estoque quando fazer uma venda
//Nome do programadores: Pedro Miguel Moraes Durço(Novato) e Helena Batista de Matos
//Data do começo: 06/07 16:43
//Ultima atualização: 07/07 16:04
public class trab { 

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		int opcao,opcao2,quantC = 0,quantA = 0, cont = 0,vendas = 0;
		double precoPM= 0, precoVM = 0,lucros = 0;
		String linha, tipo = "",linhaV;
		String[] info,infoV;
		
		File arqM,arqL;
		arqM = new File("mascara.txt");
		arqL = new File("lucro.txt");
		
	
		System.out.println("Qual opção você gostaria de usar");
		System.out.println("1- Adicionar no Estoque");
		System.out.println("2- Venda");
		System.out.println("3 Olhar estoque");
		System.out.println("4- Olhar Lucro");
		opcao = teclado.nextInt();
		System.out.println();
		try
		{
			FileReader lerM = new FileReader(arqM);//essa parte toda ate o if(opcao) é lendo os arquivos 
			FileReader lerV = new FileReader(arqL);
			BufferedReader leitorM = new BufferedReader(lerM);
			BufferedReader leitorV = new BufferedReader(lerV);
			Scanner lerM2 = new Scanner(arqM);
			
			linha = leitorM.readLine();
			linhaV = leitorV.readLine();
			
			while(lerM2.hasNextLine())//Verificando se tem proxima linha, lendo a proxima linha e contando
			{
				lerM2.nextLine();
				cont++;
				
			}
			
			Mascara[][] estoque = new Mascara[cont][2];//Criando a matriz para armazernar valores do arquvio(cont= numero de linhas do arquivo)(2 = numero de tamanhos)
			Vendas lucro;

			for(int ln =0;ln<estoque.length;ln++)//Percorrendo a matriz estoque
			{
				info = linha.split(",");//Separando as informaçoes da linha do arquivo
				
				for(int a = 0; a < info.length;a++) //Conferindo cada parte do vetor e armazenando valores
				{
					if(a == 0)
					{
						tipo = info[a];
					}
					else if(a == 1)
					{
						quantC = Integer.parseInt(info[a]);//Transformando valor em inteiro
					}
					else if(a == 2)
					{
						quantA = Integer.parseInt(info[a]);//Transformando valor em inteiro
					}
					else if(a == 3)
					{
						precoPM = Double.parseDouble(info[a]);//Transformando valor em double
					
					}
					else if(a == 4)
					{
						precoVM = Double.parseDouble(info[a]);//Transformando valor em double
					}
					
					
					
				}
				
				for(int col = 0; col <estoque[ln].length;col++)//Passando pela coluna da matriz e conferindo se é adulta ou criança
				{
					if(col == 0) 
					{
						Mascara produto = new Mascara(tipo,quantC,precoPM,precoVM);
						estoque[ln][col] = produto;
					}
					else if(col == 1) 
					{
						Mascara produto = new Mascara(tipo,quantA,precoPM,precoVM);
						estoque[ln][col] = produto;
					}
					
				}
				
				linha = leitorM.readLine();//Lendo a proxima linha do arquivo
			}//for ln
			
			infoV = linhaV.split(",");//Separando as informaçoes da linha do arquivo de lucro
			for(int b = 0; b<infoV.length;b++) 
			{
				if(b == 1)
				{
					vendas = Integer.parseInt(infoV[b]);//Percorre o vetor e armazena o numero de vendas
				}
			}
			
			linhaV = leitorV.readLine();// lendo proxima linha do arquivo de lucro
			infoV = linhaV.split(",");
			
			for(int b = 0; b<infoV.length;b++) 
			{
				if(b == 1)
				{
					lucros = Double.parseDouble(infoV[b]);//Percorre o vetor e armazena o numero de lucro
				}
			}
			
			lucro = new Vendas(lucros,vendas);//Pegando os valores e armazenando
			
			
			
			if(opcao == 1) //Adicionando no estoque
			{
				FileWriter saida1 = new FileWriter(arqM);
				System.out.println("1- Criança | 2- Adulta");
				System.out.println("Gostaria de adicionar qual tamanho 1 ou 2: ");
				opcao = teclado.nextInt();
				
				System.out.println("Quantas unidade você gostaria de adicionar?");
				opcao2 = teclado.nextInt();
				
				
				if(opcao == 1)//Se for criança eu confiro tudo e armazeno na matriz a quantidade e atualizo no arquivo
				{
					System.out.println("1-Lisa | 2-Estampada");
					System.out.println("Lisa ou Estampada?");
					opcao = teclado.nextInt();
					
					if(opcao == 1)//Vendo se ta estampada ou lisa
					{
						quantA = estoque[0][1].getQuant();//Atualizando para a quantidade certa
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								
								if(estoque[ln][col].getNome().equals("Lisa") && col == 0)
								{
									int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualizar
									estoque[ln][col].setQuant(quantidadeCL+ opcao2);
									estoque[ln][col].salvarEst(saida1, quantA, col);//Salvando no arquivo
								}
								
							}//for
							
						}//for
						quantA = estoque[1][1].getQuant();
						estoque[1][0].salvarEst(saida1, quantA, 0);//Salvando no arquivo
						System.out.println("Adicionado");//Motrando para o usuario que ta tudo certo
						
					}//if
					else if(opcao == 2)//Vendo se ta estampada ou lisa
					{
						quantA = estoque[0][1].getQuant();//Atualizando para a quantidade certa
						estoque[0][0].salvarEst(saida1, quantA, 0);//Salvando no arquivo
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								if(estoque[ln][col].getNome().equals("Estampada") && col == 0)
								{
									int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualiza
									quantA = estoque[1][1].getQuant();//Atualizando para a quantidade certa
									estoque[ln][col].setQuant(quantidadeCL+ opcao2);
									estoque[ln][col].salvarEst(saida1, quantA, col);//Salvando no arquivo
								}
							}//for
							
						}//for
						System.out.println("Adicionado");
					}//else if
					
				}
				//Se for adulto eu confiro tudo e armazeno na matriz a quantidade
				else if(opcao == 2)
				{
					System.out.println("1-Lisa | 2-Estampada");
					System.out.println("Lisa ou Estampada?");
					opcao = teclado.nextInt();
					
					if(opcao == 1)//Vendo se ta estampada ou lisa
					{
						quantC = estoque[0][0].getQuant();//Atualizando para a quantidade certa
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								if(estoque[ln][col].getNome().equals("Lisa") && col == 1)
								{
									int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualiza
									estoque[ln][col].setQuant(quantidadeCL+ opcao2);
									estoque[ln][col].salvarEst(saida1, quantC, col);//Salvando no arquivo
									
								}
							}
							
						}
						quantC = estoque[1][0].getQuant();//Atualizando para a quantidade certa
						estoque[1][1].salvarEst(saida1, quantC, 1);
						System.out.println("Atualizado");
					}
					else if(opcao == 2)
					{
						quantC = estoque[0][0].getQuant();//Atualizando para a quantidade certa
						estoque[0][1].salvarEst(saida1, quantC, 1);
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								if(estoque[ln][col].getNome().equals("Estampada")&& col == 1)
								{
									int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualiza
									quantC = estoque[1][0].getQuant();//Atualizando para a quantidade certa
									estoque[ln][col].setQuant(quantidadeCL+ opcao2);
									estoque[ln][col].salvarEst(saida1, quantC, col);//Salvando no arquivo
									
								}
							}
							
						}
						System.out.println("Adicionado");
					}
				}
				opcao = 0;//Colocando valor de 0 em opcao para n ir pros else if
				saida1.close();
			}
			
			
			else if(opcao == 2) //Guardando valor total da venda e diminuindo no estoque
			{
				FileWriter saida1 = new FileWriter(arqM);//Declarando saida do arquivo mascara
				FileWriter saida2 = new FileWriter(arqL);// Declarando saida do arquivo lucro
				System.out.println("1- Criança | 2- Adulta");
				System.out.println("Vendeu qual tamanho 1 ou 2: ");
				opcao = teclado.nextInt();
				int erro=0;
				
				System.out.println("Quantas unidades vendeu?");
				opcao2 = teclado.nextInt();
				
				if(opcao == 1)
				{
					System.out.println("1-Lisa | 2-Estampada");
					System.out.println("Lisa ou Estampada?(coloque so um tipo)");
					opcao = teclado.nextInt();
					
					if(opcao == 1)//Vendo se ta estampada ou lisa
					{
						quantA = estoque[0][1].getQuant();//Atualizando para a quantidade certa
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								
								if(estoque[ln][col].getNome().equals("Lisa") && col == 0 )
								{
									if(estoque[ln][col].getQuant() >= opcao2)//Comparando quantidade no estoque com o numero de vendas
									{
										vendas += opcao2;//Somando vendas 
										int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualiza
										estoque[ln][col].setQuant(quantidadeCL- opcao2);//Subtraindo quantidado no estoque
										estoque[ln][col].salvarEst(saida1, quantA, col);
										
										double preco2 = estoque[ln][col].getPreco_producao();//Puxando preco de producao no estoque para atualiza
										double preco = estoque[ln][col].getPreco_vend();//Puxando preco de venda no estoque para atualiza
										lucro.setVendas(vendas);//Atualizando numero de vendas
										
										lucro.salvarLucro(saida2,lucro.calcularLucro(preco,preco2,opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
										erro = 1;
									}
									
								}
								else if(erro == 0)
								{
									estoque[ln][col].salvarEst(saida1, quantA, col);
									lucro.salvarLucro(saida2,lucro.calcularLucro(estoque[ln][col].getPreco_vend(),estoque[ln][col].getPreco_producao(),opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
									System.out.println("Não há esse numero de mascaras no estoque");
									erro++;
								}
								
							}//for
							
						}//for
						quantA = estoque[1][1].getQuant();//Atualizando para a quantidade certa
						estoque[1][0].salvarEst(saida1, quantA, 0);
						
						System.out.println("Atualizado");
						
					}//if
					
					else if(opcao == 2)//Vendo se ta estampada ou lisa
					{
						quantA = estoque[0][1].getQuant();//Atualizando para a quantidade certa
						estoque[0][0].salvarEst(saida1, quantA, 0);
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								if(estoque[ln][col].getNome().equals("Estampada") && col == 0)
								{
									if(estoque[ln][col].getQuant() >= opcao2)
									{
										vendas += opcao2;
										quantA = estoque[1][1].getQuant();//Atualizando para a quantidade certa
										int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualiza
										estoque[ln][col].setQuant(quantidadeCL- opcao2);//Subtraindo quantidado no estoque
										estoque[ln][col].salvarEst(saida1, quantA, col);
										
										double preco2 = estoque[ln][col].getPreco_producao();//Puxando preco de producao no estoque para atualiza
										double preco = estoque[ln][col].getPreco_vend();//Puxando preco de venda no estoque para atualiza
										lucro.setVendas(vendas);//Atualizando numero de vendas
										
										lucro.salvarLucro(saida2,lucro.calcularLucro(preco,preco2,opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
										erro = 1;
									}
									else if(erro == 0)
									{
										estoque[ln][col].salvarEst(saida1, quantA, col);
										lucro.salvarLucro(saida2,lucro.calcularLucro(estoque[ln][col].getPreco_vend(),estoque[ln][col].getPreco_producao(),opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
										System.out.println("Não há esse numero de mascaras no estoque");
										erro++;
									}
									
								}
								
								
							}//for
							
						}//for
						
						System.out.println("Atualizado");
					}//else if
					
				}//if
				
				else if(opcao == 2)//--------------------------------------------------
				{
					System.out.println("1-Lisa | 2-Estampada");
					System.out.println("Lisa ou Estampada?(coloque so um tipo)");
					opcao = teclado.nextInt();
					
					if(opcao == 1)//Vendo se ta estampada ou lisa
					{
						quantC = estoque[0][0].getQuant();//Atualizando para a quantidade certa
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								
								if(estoque[ln][col].getNome().equals("Lisa") && col == 1 )
								{
									if(estoque[ln][col].getQuant() >= opcao2)
									{
										vendas += opcao2;
										int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualiza
										estoque[ln][col].setQuant(quantidadeCL- opcao2);//Subtraindo quantidado no estoque
										estoque[ln][col].salvarEst(saida1, quantC, col);
										
										double preco2 = estoque[ln][col].getPreco_producao();//Puxando preco de producao no estoque para atualiza
										double preco = estoque[ln][col].getPreco_vend();//Puxando preco de venda no estoque para atualiza
										lucro.setVendas(vendas);//Atualizando numero de vendas
										
										lucro.salvarLucro(saida2,lucro.calcularLucro(preco,preco2,opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
										erro = 1;
									}
									else if(erro == 0 && col == 1)
									{
										estoque[ln][col].salvarEst(saida1, quantC, col);
										lucro.salvarLucro(saida2,lucro.calcularLucro(estoque[ln][col].getPreco_vend(),estoque[ln][col].getPreco_producao(),opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
										System.out.println("Não há esse numero de mascaras no estoque");
										erro++;
									}
								}
								
							}//for
							
						}//for
						quantC = estoque[1][0].getQuant();//Atualizando para a quantidade certa
						estoque[1][1].salvarEst(saida1, quantC, 1);
						
						System.out.println("Atualizado");
						
					}//if
					else if(opcao == 2)//Vendo se ta estampada ou lisa
					{
						quantC = estoque[0][0].getQuant();//Atualizando para a quantidade certa
						estoque[0][1].salvarEst(saida1, quantC, 1);
						for(int ln=0; ln< estoque.length;ln++)
						{
							for(int col = 0; col < estoque[ln].length;col++)
							{
								if(estoque[ln][col].getNome().equals("Estampada") && col == 1)
								{
									if(estoque[ln][col].getQuant() >= opcao2)
									{
										vendas += opcao2;
										quantC = estoque[1][0].getQuant();//Atualizando para a quantidade certa
										int quantidadeCL =estoque[ln][col].getQuant();//Guardando quantidade no estoque para atualiza
										estoque[ln][col].setQuant(quantidadeCL- opcao2);//Subtraindo quantidado no estoque
										estoque[ln][col].salvarEst(saida1, quantC, col);
										
										double preco2 = estoque[ln][col].getPreco_producao();//Puxando preco de producao no estoque para atualiza
										double preco = estoque[ln][col].getPreco_vend();//Puxando preco de venda no estoque para atualiza
										lucro.setVendas(vendas);//Atualizando numero de vendas
										
										lucro.salvarLucro(saida2,lucro.calcularLucro(preco,preco2,opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
										erro = 1;
									}
									else if(erro == 0 && col ==1)
									{
										estoque[ln][col].salvarEst(saida1, quantC, col);
										lucro.salvarLucro(saida2,lucro.calcularLucro(estoque[ln][col].getPreco_vend(),estoque[ln][col].getPreco_producao(),opcao2),vendas);//Fazendo Calculo do lucro e enviando parametros
										System.out.println("Não há esse numero de mascaras no estoque");
										erro++;
									}
									
								}//if
								
							}//for
							
						}//for
						System.out.println("Adicionado");
					}//else if
					
				}//else if
				opcao = 0;//Colocando valor de 0 em opcao para n ir pros else if
				saida1.close();
				saida2.close();
			}//else if
			
			
			else if(opcao == 3)//Vai no arquivo e olha depois mostra para o usuario quantas mascaras tem no estoque 
			{
				for(int ln=0; ln< estoque.length;ln++)
				{
					if(ln == 0) {
						System.out.println();
						System.out.println("Lisa:");
					}
					if(ln == 1)
					{
						System.out.println();
						System.out.println("Estampada:");
					}
					for(int col = 0; col < estoque[ln].length;col++)
					{
						if(col == 0)
						{
							System.out.println("Criança: "+estoque[ln][col].getQuant());
						}
						else if(col == 1)
						{
							System.out.println("Adulta: "+estoque[ln][col].getQuant());
						}
					}
				}
				
			}
			else if(opcao == 4) //Mostrando lucro
			{
				lucro.mostrar();
			}
			else
			{
				System.out.println("Opção invalida");
			}
			
			leitorM.close();
			leitorV.close();
			lerM2.close();
			
		}//try	
		
		catch(IOException ioex)
		{
			System.out.println("Erro - arquivo de entrada inexistente");
		}
			
		
		teclado.close();
	}

}
//criando as classes para criar objetos para armazenar os valores quem estao vindo do arquivo
class Mascara
{
	String nome;
	int quant;
	double preco_producao, preco_vend;
	
	public Mascara(String nome, int quant, double preco_producao, double preco_vend) {
		super();
		this.nome = nome;
		this.quant = quant;
		this.preco_producao = preco_producao;
		this.preco_vend = preco_vend;
	}
	// criando os get e sets
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getQuant() {
		return quant;
	}
	public void setQuant(int quant) {
		this.quant = quant;
	}
	
	public double getPreco_producao() {
		return preco_producao;
	}
	public void setPreco_producao(double preco_producao) {
		this.preco_producao = preco_producao;
	}
	
	public double getPreco_vend() {
		return preco_vend;
	}
	public void setPreco_vend(double preco_vend) {
		this.preco_vend = preco_vend;
	}
	
	public void salvarEst(FileWriter said,int quant2,int Ca) throws IOException
	{
		//conferindo se é crianca ou adulto e imprimindo de volta no arquivo com os valores atualizados
		if(Ca == 0)//crianca
		{
			said.write(getNome()+","+getQuant()+","+quant2+","+getPreco_producao()+","+getPreco_vend()+"\n");
		}
		else if(Ca == 1)//adulto
		{
			said.write(getNome()+","+quant2+","+getQuant()+","+getPreco_producao()+","+getPreco_vend()+"\n");
		}
		
	}
	
}
class Vendas
{
	int vendas;
	double lucro;
	
	public Vendas(double lucro, int vendas) {
		super();
		this.vendas = vendas;
		this.lucro = lucro;
	}
	// criando os get e sets
	public int getVendas() {
		return vendas;
	}
	public void setVendas(int vendas) {
		this.vendas = vendas;
	}
	
	public double getLucro() {
		return lucro;
	}
	public void setLucro(double lucro) {
		this.lucro = lucro;
	}
	
	public double calcularLucro(double p1, double p2, int quant)
	{
		// fazendo o calculo do lucro obtindo recebendo valores de fora
		double total;
		total = (p1-p2)*quant;
		return total;
	}
	
	public void salvarLucro(FileWriter saidL, double lucroTotal, int vendi) throws IOException
	{
		//imprimindo o lucro e a quantidade de vendas no arquivo
		lucroTotal += lucro;
		saidL.write("Vendas,"+vendi+"\n"+"Lucro,"+lucroTotal);
	}
	
	public void mostrar()
	{
		// mostrando para o usuario as informaçoes do arquivo
		System.out.println(getVendas()+" mascaras Vendidas");
		System.out.println("Lucro de R$:"+ getLucro());
	}
	
}